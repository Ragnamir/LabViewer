#include <crow.h>
#include "service.hpp"
#include "payload.hpp"

namespace po=boost::program_options;

Service::Service()
    : ServiceBase(L"BusyBeacon",
                  L"Occupation Beacon Service",
                  SERVICE_DEMAND_START,
                  SERVICE_ERROR_NORMAL,
                  SERVICE_ACCEPT_STOP),
      desc("General options"),
      th_worker()
{
    using namespace std;

    desc.add_options()
        ("port,p", po::value<uint16_t>(&port)->default_value(18080), "Assign port")
        ("addr,a", po::value<string>(&addr)->default_value("0.0.0.0"), "Specify address to bind to")
        ;
}

void Service::onStart(DWORD argc, WCHAR* argv[])
{
    handle_args(static_cast<int>(argc), argv, IN_SERVICE);

    th_worker = std::thread([this]{
        crow::SimpleApp app;

        CROW_ROUTE(app, "/")([](){
            try{
                return isbusy() ? "1" : "0";
            }
            catch(std::system_error& ex)
            {
                return ex.what();
            }
        });
        app.bindaddr(addr).port(port).multithreaded().run();
    });
}

void Service::onStop()
{
    raise(SIGINT);
    th_worker.join();
}

void Service::handle_args(int argc, WCHAR* argv[], ProcPlace place)
{
    using namespace std;

    auto print_desc = [this](){ cout << desc << endl; };

    try {
        po::wparsed_options parsed =
                po::wcommand_line_parser(argc, argv)
                .options(desc)
                .allow_unregistered()
                .run();
        po::store(parsed, vm);
        po::notify(vm);
        if(place == IN_ENTRY) {
            print_desc();
            return;
        }
    }
    catch(exception&) {
        print_desc();
        return;
    }
}

int wmain(int argc, wchar_t* argv[])
{
    Service service;
    service.handle_args(argc, argv, Service::IN_ENTRY);
    service.Run();
}
