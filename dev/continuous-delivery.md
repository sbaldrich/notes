### Why?

* Speed
* Safety
  * **How to obtain safety?**
    * Visibility
    * Fault isolation
    * Fault tolerance (Circuit breakers, bulkheads, etc.)
    * Automatic recovery

### Twelve-factor applications

* **Codebase**:
Each deployable application is tracked as one codebase checked into revision control. It may have multiple deployed instances.
* **Dependencies**:
The declared dependencies (Maven, NPM, Bundler, etc.)
* **Configuration**:
Configuration (anything that may differ between deployment environments) is injected via operating-system variables.
* **Backing Services**:
Databases, message-brokers, etc., are treated as attached resources and *consumed identically across environments*
* **Build, release, run**:
The stages of building a deployable application, bundling it with its necessary configuration and starting one or more processes from that combination are completely separated.
* **Processes**:
The app executes one or more stateless processes that share nothing. Any necessary state is externalized to backing services.
* **Port binding**:
The app is self-contained and exposes services via port binding.
* **Concurrency**:
Concurrency is achieved by scaling out processes horizontally.
* **Disposability**:
Robustness is achieved via processes that start quickly and shut down gracefully. -> elasticity.
* *dev/prod* **parity**:
Continuous delivery/deployment are enabled by keeping all environments as similar as possible.
* **Logs**:
Logs are treated as event-streams, allowing the execution environment to collect, aggregate, index and analyze them using centralized services.
* **Admin processes**:
Management tasks such as database migration. They are executed as one-off processes in environments identical to app's long running processes.

### References
* [Release it!](http://www.amazon.com/Release-Production-Ready-Software-Pragmatic-Programmers/dp/0978739213/ref=sr_1_1?ie=UTF8&qid=1447856550&sr=8-1) - M. Nygard.
