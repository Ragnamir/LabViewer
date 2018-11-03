#include <winsock2.h>
#include <windows.h>
#include <wtsapi32.h>
#include <ws2tcpip.h>
#include <iphlpapi.h>
#include <iostream>
#include <system_error>
#include <boost/asio/ip/address.hpp>

#if !defined(HKEY_CURRENT_USER_LOCAL_SETTINGS)
// Usually defined in the Windows SDKs for Vista+ but absent in MinGW
#define HKEY_CURRENT_USER_LOCAL_SETTINGS ((HKEY) (ULONG_PTR)((LONG)0x80000007))
#endif
#include <WinReg/WinReg.hpp>

#include "payload.hpp"

struct WTSEnumerateSessionsC {
    PWTS_SESSION_INFOA pinfo=nullptr;
    DWORD count;
    WTSEnumerateSessionsC():
        pinfo(nullptr),
        count(0)
    {
        if (!WTSEnumerateSessionsA(WTS_CURRENT_SERVER_HANDLE, 0, 1, &pinfo, &count)) {
            throw std::system_error(EINTR, std::generic_category(), "sessions enumeration failure");
        }
    }
    ~WTSEnumerateSessionsC()
    {
        WTSFreeMemory(pinfo);
    }
};

struct GetTcpTableC {
    PMIB_TCPTABLE p_table;
    GetTcpTableC():
        p_table(heapAlloc(sizeof (MIB_TCPTABLE)))
    {
        DWORD tbl_size = 0;
        DWORD ret_val = 0;
        if (p_table == nullptr) {
            throw std::system_error(EINTR, std::generic_category(), "allocating memory failure");
        }
        tbl_size = sizeof (MIB_TCPTABLE);
        ret_val = GetTcpTable(p_table, &tbl_size, TRUE);
        if (ret_val == ERROR_INSUFFICIENT_BUFFER) {
            heapFree(p_table);
            p_table = heapAlloc(tbl_size);
            if (p_table == nullptr) {
                throw std::system_error(EINTR, std::generic_category(), "allocating memory failure");
            }
        }
        ret_val = GetTcpTable(p_table, &tbl_size, TRUE);
        if (ret_val != NO_ERROR) {
            throw std::system_error(EINTR, std::generic_category(), "GetTcpTable failed");
        }
    }
    ~GetTcpTableC()
    {
        if (p_table != nullptr) {
            heapFree(p_table);
        }
    }
    static MIB_TCPTABLE* heapAlloc(DWORD size)
    {
        return reinterpret_cast<MIB_TCPTABLE*>(HeapAlloc(GetProcessHeap(), 0, size));
    }
    static void heapFree(PMIB_TCPTABLE table)
    {
        HeapFree(GetProcessHeap(), 0, table);
    }
};

////////////////////////////////////////////////////////////////////////////////

bool isbusy()
{
    using namespace std;

    WTSEnumerateSessionsC senumer;
    for (size_t i=0; i < senumer.count; ++i)
    {
        if (string::npos != string(senumer.pinfo[i].pWinStationName).find("RDP-Tcp#")
                && WTSActive == senumer.pinfo[i].State) {
            return true;
        }
    }
    return false;
}

VectorStr ip_get()
{
    VectorStr result;
    GetTcpTableC tcp_table;
    winreg::RegKey key{
        HKEY_CURRENT_USER,
                L"system\\currentcontrolset"
                "\\control\\terminal server\\winstations\\rdp-tcp"
    };
    DWORD port;
    try {
        port = htons(static_cast<u_short>(key.GetDwordValue(L"PortNumber")));
    } catch(winreg::RegException) {
        port = htons(3389);
    }
    auto& p_table = tcp_table.p_table;
    for (DWORD i=0; i < p_table->dwNumEntries; ++i)
    {
        if (p_table->table[i].dwState == MIB_TCP_STATE_ESTAB
                && p_table->table[i].dwLocalPort == port)
        {
            result.emplace_back(
                        boost::asio::ip::make_address_v4(
                            ntohl(p_table->table[i].dwRemoteAddr)
                            ).to_string()
                        );
        }
    }
    return result;
}

VectorStr users_get()
{
    VectorStr result;
    if (isbusy()) {
        result = ip_get();
    }
    return result;
}
