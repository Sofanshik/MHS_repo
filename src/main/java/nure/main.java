package nure;

import com.backendless.*;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.FileInfo;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.exceptions.BackendlessException;
import com.backendless.logging.*;
import com.backendless.logging.Logger;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;

import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;

public class main {
    public static final String APPLICATION_ID = "DA1D7B8D-FE14-688E-FF99-4251CF311100";
    public static final String API_KEY = "0B92B45D-A5B5-4D27-B5D3-DFFA4BF7D1DA";

    public static void main(String[] args) throws Exception {
        Backendless.initApp(APPLICATION_ID, API_KEY);

        Scanner in = new Scanner(System.in);

        System.out.println(">>>>>>>>>>>>>>> Hello <<<<<<<<<<<<<<\n");

        while(true) {
            System.out.println(">>>>>>>>>>>>>>> Choose what are you want to do and write one of this number: <<<<<<<<<<<<<<\n");
            System.out.println("1) Registration user! \n");
            System.out.println("2) LogIn! \n");
            System.out.println("3) Forgot your password? \n");
            System.out.println("4) LogOut! \n");
            System.out.println("5) Update information about yourself! \n");
            System.out.println("6) Look information about yourself! \n");
            System.out.println("7) Work with files! \n");
            System.out.println("8) Add your location! \n");
            System.out.println("9) Work with places! \n");
            System.out.println("10) Friends \n");
            System.out.println("11) Exit! \n");

            boolean while_per = true;
            String choise = in.nextLine();

            switch (choise) {
                case ("1"):
                    System.out.println("\n>>>>>>>>>>>>>>>>>> Registration <<<<<<<<<<<<<<<<<<\n");
                    BackendlessUser user = new BackendlessUser();

                    System.out.println("Enter your name: ");
                    choise = in.nextLine();
                    user.setProperty("name", choise);

                    System.out.println("Enter your age: ");
                    choise = in.nextLine();
                    user.setProperty("age", Integer.parseInt(choise));

                    System.out.println("Enter your country: ");
                    choise = in.nextLine();
                    user.setProperty("country", choise);

                    System.out.println("Choose gender(m, f): ");
                    choise = in.nextLine();

                    if (choise.equals("m")){
                        user.setProperty("sex", "M");
                    }
                    else if (choise.equals("f")){
                        user.setProperty("sex", "F");
                    }
                    else {
                        user.setProperty("sex", "No coments");
                    }

                    System.out.println("Enter your login: ");
                    choise = in.nextLine();
                    user.setProperty("login", choise);

                    System.out.println("Enter your email: ");
                    choise = in.nextLine();
                    user.setProperty("email", choise);

                    System.out.println("Enter your password: ");
                    choise = in.nextLine();
                    user.setPassword(choise);

                    Backendless.UserService.register(user);
                    break;

                case ("2"):
                    System.out.println("\n>>>>>>>>>>>>>>>>>> LogIn <<<<<<<<<<<<<<<<<<\n");
                    System.out.println("Enter your email: ");
                    choise = in.nextLine();
                    String email = choise;
                    System.out.println("Enter your password: ");
                    choise = in.nextLine();
                    try {
                        Backendless.UserService.login(email, choise);
                        System.out.println("Login successful!");
                    } catch (BackendlessException e) {
                        System.out.println("Login failed. Error: " + e.getMessage());
                        Logger logger = Logger.getLogger("LOGinN");
                        logger.error("Login failed: " + e.getMessage());
                    }
                    break;

                case ("3"):
                    System.out.println("\n>>>>>>>>>>>>>>>>>> Restore password <<<<<<<<<<<<<<<<<<\n");
                    System.out.print("Enter your email: ");
                    email = in.nextLine();
                    Backendless.UserService.restorePassword(email);
                    break;

                case ("4"):
                    if (Backendless.UserService.CurrentUser() == null){
                        System.out.println("\n>>>>>>>>>>>>>>>>>> You aren't login! <<<<<<<<<<<<<<<<<<\n");
                    }
                    else{
                        System.out.println("\n>>>>>>>>>>>>>>>>>> LogOut <<<<<<<<<<<<<<<<<<\n");
                        Backendless.UserService.logout();
                    }
                    break;

                case ("5"):
                    if (Backendless.UserService.CurrentUser() == null){
                        System.out.println("\n>>>>>>>>>>>>>>>>>> You aren't login! <<<<<<<<<<<<<<<<<<\n");
                        break;
                    }
                    else {
                        while (while_per) {
                            if (Backendless.UserService.CurrentUser() != null) {
                                System.out.println("\n>>>>>>>>>>>>>>>>>> What information are you wanted to update? <<<<<<<<<<<<<<<<<<\n");
                                System.out.println("1) Name! \n");
                                System.out.println("2) Login! \n");
                                System.out.println("4) Password! \n");
                                System.out.println("5) Age! \n");
                                System.out.println("6) Sex! \n");
                                System.out.println("7) Country! \n");
                                System.out.println("8) Exit! \n");
                                choise = in.nextLine();

                                switch (choise) {
                                    case ("1"):
                                        System.out.println("Enter your new name: \n");
                                        String name = in.nextLine();
                                        Backendless.UserService.CurrentUser().setProperty("name", name);
                                        Backendless.UserService.update(Backendless.UserService.CurrentUser());
                                        break;

                                    case ("2"):
                                        System.out.println("Enter your new login: \n");
                                        String login = in.nextLine();
                                        Backendless.UserService.CurrentUser().setProperty("login", login);
                                        Backendless.UserService.update(Backendless.UserService.CurrentUser());
                                        break;

                                    case ("4"):
                                        System.out.println("Enter your new password: \n");
                                        String password = in.nextLine();
                                        Backendless.UserService.CurrentUser().setProperty("password", password);
                                        Backendless.UserService.update(Backendless.UserService.CurrentUser());
                                        break;

                                    case ("5"):
                                        System.out.println("Enter your new age: \n");
                                        String age = in.nextLine();
                                        Backendless.UserService.CurrentUser().setProperty("age", age);
                                        Backendless.UserService.update(Backendless.UserService.CurrentUser());
                                        break;

                                    case ("6"):
                                        System.out.println("Enter your new sex (m/f): \n");
                                        String sex = in.nextLine();
                                        if (sex.equals("m")) {
                                            Backendless.UserService.CurrentUser().setProperty("sex", "M");
                                        } else if (sex.equals("f")) {
                                            Backendless.UserService.CurrentUser().setProperty("sex", "F");
                                        } else {
                                            Backendless.UserService.CurrentUser().setProperty("sex", "No coments");
                                        }
                                        Backendless.UserService.update(Backendless.UserService.CurrentUser());
                                        break;

                                    case ("7"):
                                        System.out.println("Enter your new country: \n");
                                        String country = in.nextLine();
                                        Backendless.UserService.CurrentUser().setProperty("country", country);
                                        Backendless.UserService.update(Backendless.UserService.CurrentUser());
                                        break;

                                    case ("8"):
                                        while_per = false;
                                        break;

                                    default:
                                        break;
                                }
                            }
                        }
                    }
                    break;

                case ("6"):
                    System.out.println("\n>>>>>>>>>>>>>>>>>> Information about yourself <<<<<<<<<<<<<<<<<<\n");
                    user = Backendless.UserService.CurrentUser();

                    System.out.println(user);
                    break;

                case ("7"):
                    while (while_per) {

                        System.out.println("\n>>>>>>>>>>>>>>>>>> Choose what you want to do with files: <<<<<<<<<<<<<<<<<<\n");
                        System.out.println("1) Upload! \n");
                        System.out.println("2) Load! \n");
                        System.out.println("3) Delete! \n");
                        System.out.println("4) Share to someone! \n");
                        System.out.println("5) Open your files! \n");
                        System.out.println("6) Upload the avatar! \n");
                        System.out.println("7) Exit! \n");
                        choise = in.nextLine();

                        switch (choise) {

                            case ("1"):
                                System.out.print("Enter pass to file, that you want to upload: ");
                                String upload_file_pass = in.nextLine();
                                try {
                                    File upload_file = new File(upload_file_pass);
                                    Backendless.Files.upload(upload_file, "/shared with me/" + Backendless.UserService.CurrentUser().getEmail(), true);
                                }
                                catch (BackendlessException e ){
                                    Logger logger = Logger.getLogger("Upload File!");
                                    logger.error("Upload file failed: " + e.getMessage() + "| User: " + Backendless.UserService.CurrentUser().getEmail());
                                }
                                break;

                            case ("2"):
                                System.out.print("Enter name the file, that you want to load: ");
                                List<FileInfo> files = Backendless.Files.listing("/shared with me/", "*", false);

                                for (FileInfo file : files) {
                                    System.out.println("File name: " + file.getName());
                                    System.out.println("File URL: " + file.getURL());
                                }
                                String load_file_pass = in.nextLine();
                                File load_file = new File(load_file_pass);

                                System.out.print("Enter path where you want to load file: ");
                                String file_pass_load = in.nextLine();

                                String fileURL = "https://develop.backendless.com/app/ToDoList/files/shared%20with%20me/" + Backendless.UserService.CurrentUser().getEmail() + "/" + load_file_pass;

                                try {
                                    URL url = new URL(fileURL);
                                    InputStream ins = new BufferedInputStream(url.openStream());
                                    FileOutputStream fileOutputStream = new FileOutputStream(file_pass_load);

                                    byte[] buffer = new byte[1024];
                                    int bytesRead;
                                    while ((bytesRead = ins.read(buffer)) != -1) {
                                        fileOutputStream.write(buffer, 0, bytesRead);
                                    }

                                    fileOutputStream.close();
                                    ins.close();

                                    System.out.println("File downloaded");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                break;

                            case ("3"):
                                if (Backendless.UserService.CurrentUser() != null){
                                    System.out.print("Enter name the file, which you want to delete: ");
                                    files = Backendless.Files.listing("/shared with me/" + Backendless.UserService.CurrentUser().getEmail(), "*", false);

                                    for (FileInfo file : files) {
                                        System.out.println("File name: " + file.getName());
                                    }
                                    String name_file = in.nextLine();
                                    Backendless.Files.remove("/shared with me/" + Backendless.UserService.CurrentUser().getEmail() + "/" + name_file);
                                }
                                else{
                                    System.out.print("You aren't LogIn");
                                }
                                break;

                            case ("4"):
                                if (Backendless.UserService.CurrentUser() != null){

                                    files = Backendless.Files.listing("/shared with me/" + Backendless.UserService.CurrentUser().getEmail(), "*", false);

                                    for (FileInfo file : files) {
                                        System.out.println("File name: " + file.getName());
                                    }
                                    System.out.print("Enter name the file, which you want to share: \n");
                                    String name_file = in.nextLine();

                                    System.out.print("Enter email recipient: \n");
                                    String recipient = in.nextLine();

                                    DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                                    queryBuilder.setProperties("email");

                                    Backendless.Data.of(Map.class).find(queryBuilder, new AsyncCallback<List<Map>>() {
                                        @Override
                                        public void handleResponse(List<Map> users) {
                                            for (Map user : users) {
                                                String email = (String) user.get("email");
                                                if (recipient.equals(email)){
                                                    Backendless.Files.copyFile("/shared with me/" + Backendless.UserService.CurrentUser().getEmail(), "/share with me/" + email);
                                                }
                                            }
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault fault) {
                                            System.out.println("Error, this user is imagine: " + fault.getMessage());
                                        }
                                    });
                                }
                                else{
                                    System.out.print("You aren't LogIn");
                                }
                                break;

                            case("5"):
//                                System.out.print("Enter name of file, that you want to open: \n");
//                                String fileName = in.nextLine();
//                                String filePath = "https://develop.backendless.com/app/ToDoList/files/shared%20with%20me/n0975426435@gmail.com/" + Backendless.UserService.CurrentUser().getEmail() + "/" + fileName;
//
//                                try {
//
//                                    // Create a temporary file to save the downloaded file
//                                    File tempFile = File.createTempFile("backendless", null);
//
//                                    // Download file from backendless server
//                                    downloadFile(filePath, tempFile);
//
//                                    // Checking if the file has been successfully uploaded
//                                    if (tempFile.exists()) {
//                                        // Running a file with java.awt.Desktop
//                                        Desktop.getDesktop().open(tempFile);
//                                    } else {
//                                        System.out.println("Fail not founded");
//                                    }
//
//                                } catch (BackendlessException | IOException e) {
//                                    e.printStackTrace();
//                                }
                                System.out.print("This function still not working(((");
                                break;

                            case ("6"):
                                if (Backendless.UserService.CurrentUser() != null){
                                    System.out.println("Additional avatar! \n");
                                    System.out.print("Enter pass to file, that you want to upload as avatar: ");
                                    upload_file_pass = in.nextLine();
                                    File upload_file = new File(upload_file_pass);
                                    Backendless.Files.upload(upload_file, "/shared with me/" + Backendless.UserService.CurrentUser().getEmail() + "/avatar/",true);
                                    Backendless.UserService.CurrentUser().setProperty("avatar", "https://backendlessappcontent.com/DA1D7B8D-FE14-688E-FF99-4251CF311100/66A38710-5B4A-4705-B1C5-58624ACB92A4/files/shared+with+me/" + Backendless.UserService.CurrentUser().getEmail() + "/avatar/" + upload_file.getName());
                                    Backendless.UserService.update(Backendless.UserService.CurrentUser());
                                }
                                else{
                                    System.out.print("You aren't login! \n");
                                }

                                break;

                            case ("7"):
                                while_per = false;
                                break;

                            default:
                                break;
                        }
                    }
                    break;

                case ("8"):
                    if (Backendless.UserService.CurrentUser() != null) {
                        Timer timer = new Timer();

                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    // Getting the user's external IP address
                                    URL url = new URL("https://api.ipify.org");
                                    URLConnection connection = url.openConnection();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                    String ip = reader.readLine();
                                    reader.close();

                                    // Requesting geolocation information by IP address
                                    URL geoUrl = new URL("http://ip-api.com/json/" + ip);
                                    URLConnection geoConnection = geoUrl.openConnection();
                                    BufferedReader geoReader = new BufferedReader(new InputStreamReader(geoConnection.getInputStream()));
                                    StringBuilder response = new StringBuilder();
                                    String line;
                                    System.out.println(response);
                                    while ((line = geoReader.readLine()) != null) {
                                        response.append(line);
                                    }
                                    geoReader.close();

                                    // Processing JSON response and extracting coordinates
                                    Gson gson = new Gson();
                                    JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                                    double latitude = jsonObject.get("lat").getAsDouble();
                                    double longitude = jsonObject.get("lon").getAsDouble();

                                    //System.out.println("Current user coordinates: " + latitude + ", " + longitude);

                                    GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
                                    Coordinate coordinate = new Coordinate(longitude, latitude);
                                    Point point = geometryFactory.createPoint(coordinate);

                                    // Convert to WKT
                                    String wkt = point.toText();

                                    //System.out.println("Current user coordinates in WKT format: " + wkt);
                                    try {
                                        Backendless.UserService.CurrentUser().setProperty("my_location", wkt);
                                        Backendless.UserService.update(Backendless.UserService.CurrentUser());
                                    }catch (BackendlessException e){
                                        Logger logger = Logger.getLogger("Set user location!");
                                        logger.error("Set user location failed: " + e.getMessage() + "| User: " + Backendless.UserService.CurrentUser().getEmail());
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        // Start a timer that repeats the task every minute (60,000 milliseconds)
                        timer.schedule(task, 0, 60000);
                    }
                    break;

                case ("9"):
                    if (Backendless.UserService.CurrentUser() != null) {

                        while (while_per) {

                            System.out.println("\n>>>>>>>>>>>>>>>>>> Choose what you want to do with locations: <<<<<<<<<<<<<<<<<<\n");
                            System.out.println("1) Add! \n");
                            System.out.println("2) Delete! \n");
                            System.out.println("3) Open all! \n");
                            System.out.println("4) Exit! \n");
                            choise = in.nextLine();

                            switch (choise) {

                                case ("1"):
                                    Place places = new Place();

                                    System.out.println("Enter name of your place: \n");
                                    places.name_place = in.nextLine();

                                    System.out.println("Enter description of your place: \n");
                                    places.description = in.nextLine();

                                    System.out.println("Enter longitude of your place: \n");
                                    double longitude = Double.parseDouble(in.nextLine());
                                    System.out.println("Enter latitude of your place: \n");
                                    double latitude = Double.parseDouble(in.nextLine());

                                    GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
                                    Coordinate coordinate = new Coordinate(longitude, latitude);
                                    Point point = geometryFactory.createPoint(coordinate);
                                    places.place_cord = Place.toPoint(point);


                                    System.out.println("Enter meta information of your place: \n");
                                    places.meta_info = in.nextLine();

                                    System.out.print("Do you want add image as place photo? (y/n)\n");
                                    choise = in.nextLine();
                                    if (choise.equals("y")) {
                                        System.out.print("Enter pass to image, that you want to upload as place photo: ");
                                        String upload_file_pass = in.nextLine();
                                        File upload_file = new File(upload_file_pass);
                                        Backendless.Files.upload(upload_file, "/shared with me/" + Backendless.UserService.CurrentUser().getEmail() + "/places/", true);
                                        places.img_link = "https://backendlessappcontent.com/DA1D7B8D-FE14-688E-FF99-4251CF311100/66A38710-5B4A-4705-B1C5-58624ACB92A4/files/shared+with+me/" + Backendless.UserService.CurrentUser().getEmail() + "/places/" + upload_file.getName();
                                    } else {
                                        places.img_link = "";
                                    }

                                    Backendless.Data.of(Place.class).save(places);
                                    break;

                                case ("2"):
                                    DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                                    queryBuilder.setWhereClause("ownerId = '" + Backendless.UserService.CurrentUser().getObjectId() + "'");
                                    var places_owner = Backendless.Data.of(Place.class).find(queryBuilder);
                                    if (!places_owner.isEmpty()) {
                                        int counter = 1;

                                        for (Place place : places_owner) {
                                            System.out.println(counter + ") Place: " + place);
                                            counter++;
                                        }
                                        System.out.print("Enter the number of the object you want to delete! \n");
                                        int number_deleted = Integer.parseInt(in.nextLine());
                                        if (number_deleted >= 0 && number_deleted < places_owner.size()) {
                                            Place places_owner2 = places_owner.get(number_deleted - 1);
                                            Backendless.Data.of(Place.class).remove("objectId = '" + places_owner2.objectId + "'");
                                        } else {
                                            System.out.print("You entered wrong number! \n");
                                        }
                                    }
                                    else{
                                        System.out.print("You haven't your own places! \n");
                                    }
                                    break;

                                case ("3"):
                                    var places_all_l = Backendless.Data.of(Place.class).find();
                                    int counter = 1;

                                    for (var place : places_all_l) {
                                        System.out.println(counter + ") Place: " + place);
                                        counter++;
                                    }
                                    break;

                                case ("4"):
                                    while_per = false;
                                    break;
                                    
                                default:
                                    break;
                            }
                        }
                    }else {
                        System.out.print("You aren't login! \n");
                    }
                    break;

                case ("10"):
                    if (Backendless.UserService.CurrentUser() != null){
                        while(while_per){

                            System.out.println("\n>>>>>>>>>>>>>>>>>> Friends: <<<<<<<<<<<<<<<<<<\n");
                            System.out.println("1) Add! \n");
                            System.out.println("2) Delete! \n");
                            System.out.println("3) Search! \n");
                            System.out.println("4) Exit! \n");
                            choise = in.nextLine();

                            switch (choise){

                                case ("1"):
                                    System.out.println("Enter email of your friends! \n");
                                    String email_friend = in.nextLine();

                                    break;

                                case ("2"):
                                    System.out.println("Enter email of your friends which you want to delete! \n");
                                    email_friend = in.nextLine();

                                case ("3"):
                                    System.out.println("Enter email of your friends which you want to search! \n");
                                    email_friend = in.nextLine();

                                case ("4"):
                                    while_per = false;
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                    else{
                        System.out.print("You aren't login! \n");
                    }

                case ("11"):
                    System.exit(0);
                    break;

                default:
                    break;
            }
        }

        //REGISTER
        /*
        BackendlessUser user = new BackendlessUser();
        user.setProperty("email", "asshbvfd@gmail.com");
        user.setProperty("login", "JustLogin");
        user.setPassword("000000");
        Backendless.UserService.register(user);
        System.out.println("!!!==============Exelent================!!!");*/

        //LOGIN
        //BackendlessUser user = Backendless.UserService.login( "JustLogin", "000000", true );

        //UPDATE
        ///BackendlessUser user = Backendless.UserService.CurrentUser();
        //user.setProperty("age", 20);
        //Backendless.UserService.update(user);


        //LOGOUT

    }

//    private static void downloadFile(String fileURL, File outputFile) throws IOException {
//        URL url = new URL(fileURL);
//        try (InputStream in = url.openStream()) {
//            Files.copy(in, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//        }
//    }

//    private static final Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new GsonDateSerelisable()).create();
//
//    static Place ToPlace(Map<String, Object> map){
//        var Json = gson.toJson(map);
//        return gson.fromJson(Json, Place.class);
//    }

}


