//types and Constants
enum VehicleType {
  CAR,
  TRUCK,
  ELECTRIC,
  VAN,
  MOTORBIKE,
}

enum ParkingSpotType {
  HANDICAPPED,
  COMPACT,
  LARGE,
  MOTORBIKE,
  ELECTRIC,
}

enum AccountStatus {
  ACTIVE,
  BLOCKED,
  BANNED,
  COMPROMISED,
  ARCHIVED,
  UNKNOWN,
}

enum ParkingTicketStatus {
  ACTIVE,
  PAID,
  LOST,
}

class Address {
  private String streetAddress;
  private  String city;
  private String state;
  private String zipCode;
  private String country;
}

class Person {
  private String name;
  private Address address;
  private String email;
  private String phone;
}

//Basically two types of account in our system Admin and Parking Attendant

abstract class Account {
  //Most of the method have public getter and setter function
  private String userName;
  private String password;
  private Person person;
  private AccountStatus status;

  public boolean resetPassword(){
    //Here will be the mechanism
    //To avoid compilation errors returning true
    return true;
  }
}

class Admin extends Account{
  public bool addParkingFloor(ParkingFloor floor);
  public bool addParkingSpot(string floorName, ParkingSpot spot);
  public bool addParkingDisplayBoard(String floorName, ParkingDisplayBoard displayBoard);
  public bool addCustomerInfoPanel(String floorName, CustomerInfoPanel infopanel);

  public bool addEntrancePanel(EntrancePanel entrancePanel);
  public bool addExitPanel(ExitPanel exitPanel);
}

class ParkingAttendant extends Account {
  public bool processTicket(String ticketNumber);
}

class ParkingLot{
  public static void main(String args[]) {
    System.out.println("Design a parking lot");
  }
}
