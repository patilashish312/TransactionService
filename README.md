# TransactionService
Spring boot based Account balance transaction webservice.

This case study is Account transaction API.

To run this project, we need to run `SpringBootStart.java` as java application.


This Web service do mainly following operations-
1. Get Accounts and balances 
2. Register new account 
3. Transfer amount from source account to destination account.

Deatils of each operations are as follows-

1. Get Accounts and balances-
 We can use 'GET' service to get details of existing accounts & their current balances. 
 
 Sample input- 
 `curl http://localhost:8080/getAccountsAndBalances` 
 
 Sample output-
 `[{"accountNumber":3,"balance":0.0},{"accountNumber":2,"balance":200.0},{"accountNumber":1,"balance":100.0}]`
 
 2. Register new account -
 Registering new account into system.We can register new account into system with initial balance. In current session if we try to 
 register same account again, system will give message saying account already registered.
 
 Sample input-
 `curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"accountNumber":"1", "balance":"100.00"}' http://localhost:8080/registerAccount`
 
 Sample Output-
 `Account registered successfully`
 
 3.Transfer amount from source account to destination account.
 We can transfer amount from source account to destination account. 
 Precheck for successful execution of service includes:
 sufficient balance, amount not in negative format,source and destination is different account, source and destination is already registered.
  
Sample input-
`curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"srcAccountNumber":"3","destinationAccNumber":"2","amount":"100"}' http://localhost:8080/transferBalance`

Sample output-
`Transaction is successful.Revised Balance is follow:Source Account [accountNumber=3, balance=0.0]
Destination Account [accountNumber=2, balance=200.0]`
