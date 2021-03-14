import Graphs.*;
import java.util.Scanner;
import java.util.Iterator;

public class Network {

    private Scanner sc = new Scanner(System.in);
    private UndirectedGraph<Profile> ProfileNetwork;
    private LinkedDictionary<String,Profile> TotalUsers;

    Network(){
        ProfileNetwork = new UndirectedGraph<>();
        TotalUsers = new LinkedDictionary<>();
    }

    public Profile addUser(String username,String name, String status){

        Profile newProfile = new Profile(name,status,username);

        if (!TotalUsers.contains(username)){
            ProfileNetwork.addVertex(newProfile);
            TotalUsers.add(username,newProfile);
            System.out.println("Profile Created:"+username);
            return newProfile;
        }
        else{
            System.out.println("ERROR: Username taken.");
            return null;
        }
    }
    public boolean deleteUser(String username){
        if(TotalUsers.contains(username)){
            Profile removedUser = TotalUsers.remove(username);
            ProfileNetwork.removeVertex(removedUser);
            return true;
        }else
            System.out.println("ERROR: No Profile with this username exists");
            return false;
    }
    public void modifyUser(String username){
        Scanner scanner = new Scanner(System.in);
        Profile profile = null;
        if (TotalUsers.contains(username)){
            profile = TotalUsers.getValue(username);
        }
        if(profile != null){
            System.out.println("Do you wish to modify name? (y/n)");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Please enter your new name");
                profile.setName(scanner.nextLine());
                System.out.println("New Profile Details: \n" + profile.toString());
            }
            System.out.println("Do you wish to modify status (y/n)");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Please enter your new status");
                profile.setStatus(scanner.nextLine());
                System.out.println("New Profile Details: \n" + profile.toString());
            }
        }else{
            System.out.println("ERROR: No Profile with this username exists");
        }

    }
    public void createFriend(String originUsername,String destUsername){
        Profile originUser = TotalUsers.getValue(originUsername);
        Profile destUser = TotalUsers.getValue(destUsername);
        ProfileNetwork.addEdge(originUser,destUser);
        System.out.println("Added "+destUsername);
    }
    public void removeFriend(String originUsername,String destUsername){
        Profile originUser = TotalUsers.getValue(originUsername);
        Profile destUser = TotalUsers.getValue(destUsername);
        ProfileNetwork.removeEdge(originUser,destUser);
        System.out.println("Removed "+destUsername);

    }
    public void displayProfile(String username){
        if (TotalUsers.contains(username)){
            Profile user = TotalUsers.getValue(username);
            System.out.println(username+"  Profile Details");
            System.out.println("----------------------------");
            System.out.println(user.toString());
            ListWithIteratorInterface<VertexInterface> friendsList = ProfileNetwork.getNeighbors(user);
            Iterator<VertexInterface> friendIterator = friendsList.getIterator();
            System.out.println("# OF FRIENDS: "+friendsList.getLength());
            System.out.println("----------------------------");
            while (friendIterator.hasNext()){
                System.out.println(friendIterator.next().getLabel().toString());
                System.out.println("----------------------------");
            }
        }
        else{
            System.out.println("ACCOUNT NOT FOUND");
        }



    }
    public boolean contains(String username){
        return TotalUsers.contains(username);
    }
    public boolean containsFriend(String originUsername,String destUsername){
        Boolean result = false;
        Profile originUser = TotalUsers.getValue(originUsername);
        Profile destUser = TotalUsers.getValue(destUsername);
        Iterator<VertexInterface> friendsList = ProfileNetwork.getNeighbors(originUser).getIterator();
        while (friendsList.hasNext()){
            if(friendsList.next().getLabel().equals(destUser)){
                result = true;
                System.out.println("Friend found in origin");
            }
        }
        return result;
    }



}
