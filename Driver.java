
import Graphs.LinkedDictionary;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Network network = new Network();
        Scanner sc = new Scanner(System.in);
        boolean quit = false;
        while (true) {
            System.out.println("\nMENU" +"\n=================" + "\n0. Exit" + "\n1. Create a new profile"
                    + "\n2. Modify a profile" + "\n3. Delete a profile" + "\n4. Search a profile"
                    + "\n5. Add friend to a profile" + "\n6. Remove friend from a profile" + "\n7. Modify Friends"+"\n8. Show a Friend's list");
            String choice = sc.nextLine();
            int selection = Integer.parseInt(choice);
            switch (selection) {
                case 1:// create a profile
                    //get the Dict
                    boolean isValid = false;
                    String userName = null;

                    while(!isValid){
                        System.out.println("Enter your username");
                        userName = sc.nextLine();
                        if (network.contains(userName)){
                            System.out.println("INVALID: USERNAME ALREADY TAKEN");
                        }else{
                            isValid = true;
                        }

                    }

                    System.out.println("Enter your Name");
                    String name = sc.nextLine();
                    System.out.println("Please enter the status: ");
                    String status = sc.nextLine();
                    network.addUser(userName,name, status);
                    network.displayProfile(userName);
                    break;

                case 2:// Update a profile
                    System.out.println("Enter the username");
                    network.modifyUser(sc.nextLine());
                    break;

                case 3:// delete a profile
                    System.out.println("Enter the username");
                    network.deleteUser(sc.nextLine());
                    break;

                case 4:// Read or search a profile
                    System.out.println("Enter the username");
                    network.displayProfile(sc.nextLine());
                    break;

                case 5:// add friend to a profile
                    System.out.println("Enter the username you wish to add friends from");
                    String origin = sc.nextLine();
                    if (!network.contains(origin))
                        System.out.println("ERROR: No Profile with this username exists");
                    else {
                        while (true) {
                            System.out.println("Enter your friend's username");
                            String friend = sc.nextLine();
                            if (!network.contains(friend))
                                System.out.println("ERROR: No Profile with this username exists");
                            else {
                                network.createFriend(origin,friend);
                                network.displayProfile(origin);
                            }
                            System.out.println("Do you wish to add another friend for this profile (y/n)");
                            if (sc.nextLine().equalsIgnoreCase("n"))
                                break;
                        }

                    }
                    break;
                case 6:// remove a friend
                    System.out.println("Enter the username you wish to remove friends from");
                    String r = sc.nextLine();
                    if (!network.contains(r))
                        System.out.println("ERROR: No Profile with this username exists");
                    else {
                        while (true) {
                            System.out.println("Enter the friend username");
                            String f = sc.nextLine();
                            if (!network.contains(f)) {
                                System.out.println("ERROR: No Profile with this username exists");
                                break;
                            } else {
                                network.removeFriend(r,f);
                                network.displayProfile(r);
                            }
                            break;

                        }
                    }
                    break;

                case 7:
                    System.out.println("Enter the username");
                    String t = sc.nextLine();
                    if (!network.contains(t))
                        System.out.println("ERROR: No Profile with this username exists");
                    else {
                        while (true) {
                            System.out.println("Enter the friend username");
                            String friend = sc.nextLine();
                            if (!network.containsFriend(t,friend))
                                System.out.println("ERROR: No Profile with this username exists in your friends list.");
                            else {
                                network.modifyUser(friend);
                            }
                            break;
                        }

                    }
                    break;

                case 8:
                    System.out.println("Enter the username");
                    String j = sc.nextLine();
                    if (!network.contains(j))
                        System.out.println("ERROR: No Profile with this username exists");
                    else {
                        while (true) {
                            System.out.println("Enter the friend username");
                            String friend = sc.nextLine();
                            if (!network.containsFriend(j,friend))
                                System.out.println("ERROR: No Profile with this username exists in your friends list.");
                            else {
                                network.displayProfile(friend);
                            }
                            break;
                        }

                    }
                    break;
                default:
                    System.out.println("Exiting");
                    quit = true;
                    sc.close();
                    break;
            }
            if (quit) {
                break;
            }
        }
    }
}
