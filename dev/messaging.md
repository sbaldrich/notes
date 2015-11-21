## Application messaging

Suppose you have a human resources application that at first contains employee data.
A new component of the system requires that a tax record is sent to the local government whenever a new employee is added. Now a payroll system needs to create an entry for the new employee so the employee service is updated to call the payroll service. Now, an insurance component needs to be called in order to update management policies and yada yada yada. **This system can quickly get out of hand**

#### Enter messaging (Pub / Sub)

Messaging is a way to decouple systems like this. It is composed of a **broker**, in charge of keeping track of who's following which *topics*. **Publishers**, that publish messages and **subscribers** that listen for these messages (at least the ones they're interested in). **You can never have more than one [logical] broker**
