import java.util.Date;
import java.util.List;
import java.time.*;
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
  COMPLETED,
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

class Constants{
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

  public String getID() {
    return id;
  }
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

class Member extends Account {
  //Both of this private member has public getter function
  private Date dateOfMembership;
  private int totalBooksCheckedOut;

  // will be implemented after implementing book item
  public boolean reserveBookItem(BookItem bookItem){
    //Implement this logic Here
    //To avoid compilation error return true
    return true;
  }

  private void incrementTotalBooksCheckedout(){
    this.totalBooksCheckedOut++;
  }

  private void decrementTotalBooksCheckedout(){
    this.totalBooksCheckedOut--;
  }

  //will be implementedafter implemeting bookItem
  public boolean checkOutBookItem(BookItem bookItem){
    if(this.totalBooksCheckedOut >= Constatnts.MAX_BOOKS_ISSUED_TO_A_USER) {
      //Impelement a function Showerror to display errors
      //Showerror("The user has already checked-out maximum number of books");
      return false;
    }
    BookReservation  bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
    if(bookReservation != null && bookReservation.getMemberID() != this.getID()) {
      //Impelement a function Showerror to display errors
      //Showerror("This book item has pending Reservation");
      return false;
    }
    else{
      bookReservation.updateStatus(ReservationStatus.COMPLETED);
    }

    if(!bookItem.checkOut(this.getID())) {
      return false;
    }
    this.incrementTotalBooksCheckedout();
    return true;
  }

 //will be implemented later
 public void checkForFine(String bookItemBarcode){
   BookLending bookLending = BookLending.fetchLendingDetails(bookItemBarcode);
   Date dueDate = bookLending.getDueDate();
   Date today = new Date();
   if(today.compareTo(dueDate) > 0){
     long diff = today.getTime() - dueDate.getTime();
     long diffDays = diff / (24 * 60 * 60 * 1000);
     Fine.collectFine(this.getID(), diffDays);
   }
 }

 private void returnBookItem(BookItem bookItem){
   this.checkForFine(bookItem.getBarcode());
   BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
   if(bookReservation != null) {
     bookItem.updateBookItemStatus(BookStatus.RESERVED);
     bookReservation.sendBookAvailableNotification();
   }
   else{
     bookItem.updateBookItemStatus(BookStatus.AVAILABLE);
   }
   this.decrementTotalBooksCheckedout();
  }

  public boolean renewBookItem(BookItem bookItem){
     this.checkForFine(bookItem.getBarcode());
     BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
     if(bookReservation != null && bookReservation.getMemberID() != this.getID()){
       //Implement Showerror methods
       //Showerror("This book is RESERVED by another member");
       this.decrementTotalBooksCheckedout();
       bookItem.updateBookItemStatus(BookStatus.RESERVED);
       bookReservation.sendBookAvailableNotification();
       return false;
     }
     else if(bookReservation != null){
       bookReservation.updateStatus(ReservationStatus.COMPLETED);
     }

     BookLending.lendBook(bookItem.getBarcode(), this.getID());
     //Date today = new Date();
//updating due date
     //bookItem.updateDueDate(LocalDate.now().plusDays(Constants.MAX_LENDING_DAYS));
     return true;
  }
}

//BookReservation, BookLending and checkFor Fine
class BookReservation{
  private Date creationDate;
  private ReservationStatus status;
  private String bookItemBarcode;
  private String memberId;

  public static BookReservation fetchReservationDetails(String barcode) {
    //Impelement the logic Here
    //To avoid error returning BookReservation
    return new BookReservation();
  }
  public void updateStatus(ReservationStatus status) {
    this.status = status;
  }
  public void sendBookAvailableNotification() {

  }
  public String getMemberID() {
    return this.memberId;
  }
}

class BookLending{
  private Date creationDate;
  private Date dueDate;
  private Date returnDate;
  private String bookItemBarcode;
  private String memberId;

  public Date getDueDate() {
    return this.dueDate;
  }
  public static boolean lendBook(String barcode, String memberId){
    //Implement the processing of lending book
    //To avoid compile error return true
    return true;
  }

//This function is not implemented it should return all information about
//returning book;
  public static BookLending fetchLendingDetails(String barcode) {
    //Here should be the implementation
    //To avoid compile error return just an demo object;
    return new BookLending();
  }
}

class Fine{
  private Date creationDate;
  private double bookItemBarcode;
  private String memberId;

  public static void collectFine(String memberId, long days){
    //implementation of collecting fine
  }
}


//BookItem This class will be responsible for processing  Reservation, renewal
//and return of a book item

abstract class Book{
  //Assume All of the attributes of this has getter methods
  private String ISBN;
  private String title;
  private String subject;
  private String publisher;
  private String language;
  private int numberOfPages;
  private List<Person> authors;
}

class BookItem extends Book{
  private String barcode;
  private boolean isReferrnceOnly;
  private Date borrowed;
  private Date dueDate;
  private double price;
  private BookFormat bookFormat;
  private BookStatus status;
  private Date dateOfPurchase;
  private Date publicationDate;
  private Rack placedAt;

  public String getBarcode(){
    return barcode;
  }

  public boolean checkOut(String memberId){
    if(this.isReferrnceOnly){
      //An service Showerror need to implemented
      //Showerror("This book is reference only and can't be issued");
      return false;
    }
    if(!BookLending.lendBook(this.barcode, memberId)){
      return false;
    }
    this.status = BookStatus.LOANED;
    return true;
  }
  public void updateBookItemStatus(BookStatus status){
    this.status = status;
  }
  public  void updateDueDate(Date date) {
    this.dueDate = date;
  }
}

class Rack{
  private int number;
  private String locationIdentifier;
}


public class Library {
  public static void main(String args[]) {

    System.out.println("Library management system");
  }

}
