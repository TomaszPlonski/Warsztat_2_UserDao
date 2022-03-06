# UserDao
This is data access object class for easier work with user management in the database.

Class uses java for reading, editing and removing users from sql database

# What is my purpose?
Main methods in this class are:
- DBUtil.connect() - please adjust your login details individually
- UserDao.createUser(user) - put new user to database
- UserDao.read(userID) - read data of user of given id
- UserDao.deleteUser(userId) - delete user of given id from database
- UserDao.readAll() - return ArrayLst of all users in database

I'll be using this UserDao on my next more advanced user maintenance project.



<b>Like always any comments and advice will be appreciated</b>

