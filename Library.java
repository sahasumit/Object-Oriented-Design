import java.util.Date;


//Enum and constatnts
 enum BookFormat{
  HARDCOVER,
  PAPERBACK,
  AUDIO_BOOK,
  EBOOK,
  NEWSPAPER,
  MAGAZINE,
  JOURNAL,
}

enum BookStatus{
  AVAILABLE,
  RESERVED,
  LOANED,
  LOST,
}

enum ReservationStatus{
  WAITING,
  PENDING,
  CANCELED,
  NONE,
}

enum AccountStatus {
  ACTIVE,
  CLOSED,
  CANCELED,
  BLACKLISTED,
  NONE,
}

class Address{
  private String streetAddress;
  private String city;
  private String state;
  private String zipCode;
  private String country;
}

class Person{
  private String name;
  private Address address;
  private String email;
  private String phone;
}

class Constatnts{
  public static final int MAX_BOOKS_ISSUED_TO_A_USER = 5;
  public static final int MAX_LENDING_DAYS = 10;
}

//Account, Memebr and Librarian: these class represent the system that interact with out systems

//Assume that all class attributes are private and accessed through their respective
//public getter method and modified only through their public method function

abstract class Account {
  //Assume Without  password all other attributes has public function to
  //get their value
  private String id;
  private String password="abc";
  private AccountStatus status;
  private Person person;

  public boolean resetPassword(){
    // Here the logic for resetting password will be implemented.
    //Return true if password resetting is successfull otherwisw false.
    // Not to get the compile error we are writing return true;
    return true;
  }

}

class Librarian extends Account{
  //they are commented out as they are not implemented yet
  //public boolean addBookItem(BookItem);
//  public boolean blockMember(Member member);
  //public boolean unblockMember(Member member);
}

class MemberAccount extends Account {
  //Both of this private member has public getter function
  private Date dateOfMembership;
  private int totalBooksCheckedOut;

// will be implemented after implementing book item
// public boolean reserveBookItem(BookItem bookItem);

  private void incrementTotalBooksCheckedout(){
    this.totalBooksCheckedOut++;
  }

//will be implementedafter implemeting bookItem
/*public boolean checkOutBookItem(BookItem bookItem){

  }*/

 //will be implemented later
 /*public void checkForFine(){

}

private void returnBookItem() {

}

public bool renewBookItem() {

}
 */
}


public class Library {
  public static void main(String args[]) {

    System.out.println("Library management system");
  }

}
