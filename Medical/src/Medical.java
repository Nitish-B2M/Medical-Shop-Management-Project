import java.util.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//Interheritance Abstract Interface File Handling Constructor Date JDBC Exception Handling

abstract class credentials { // Abstract Class
  final String shop_name = "MEDICAL SHOP MANAGEMENT";

  abstract void login(); // Abstract Login Function

}

interface display_medicine { // Interface
  void display_med_Det(); // With 3 Function

  void display_med_Sup();

  void display_med_User();
}

class shop extends credentials implements display_medicine { // Implement Interface

  String owner_name, owner_pass, med_name, med_category, med_cmp_name, sup_cmp_name, cmp_id;
  int Sr_no, med_price, med_quantity;
  int rf_num[] = new int[10];
  int rf_count[] = new int[10];

  Scanner sc = new Scanner(System.in);
  int ch;
  Connection con;

  shop() { // constructor //Connect to Database using JDBC mysql
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAVAPROJECT", "root", "");
    } catch (Exception e) {
      System.out.print(e);
    }
  }

  String format_data(String f_str, int f_num) { // Formatting display Output
    int len = f_str.length();
    int left_len = f_num - len;
    String f_repeat = " ";
    String out_str = f_str + f_repeat.repeat(left_len);
    return out_str;
  }

  @Override
  public void display_med_Det() { // Override Interface Function
    try {
      String med_detail = "select * from MEDICINE;";
      Statement smt = con.createStatement();
      ResultSet rset = smt.executeQuery(med_detail);
      System.out.println("=========================================================================================");
      System.out.println("| Sr_no | Medicine_Name        | Price | Company_Name       | Quantity | Category       |");
      System.out.println("|---------------------------------------------------------------------------------------|");
      while (rset.next()) {
        String sr_no = Integer.toString(rset.getInt("Sr_No"));
        String f_sr_no = format_data(sr_no, 5);
        String Medicine_Name = rset.getString("Medicine_Name");
        String f_Medicine_Name = format_data(Medicine_Name, 20);
        String company_name = rset.getString("Company_Name");
        String f_campany_Name = format_data(company_name, 18);
        String price = Integer.toString(rset.getInt("Price"));
        String f_price = format_data(price, 5);
        String quantity = Integer.toString(rset.getInt("Quantity"));
        String f_quantity = format_data(quantity, 8);
        String category = rset.getString("Category");
        String f_category = format_data(category, 15);
        System.out
            .println("| " +
                f_sr_no + " | " + f_Medicine_Name + " | " + f_price + " | " + f_campany_Name + " | " + f_quantity
                + " | "
                + f_category + "|");
      }
      System.out.println("=========================================================================================");
      rset.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Override
  public void display_med_Sup() { // Override Interface Function
    try {
      String sup_detail = "select * from Suppliers;";
      Statement smt = con.createStatement();
      ResultSet rset = smt.executeQuery(sup_detail);
      System.out.println("=================================================");
      System.out.println("| Sr_No  " + " | " + "Company Name       " + " | " + "Company ID    |");
      System.out.println("|-----------------------------------------------|");
      while (rset.next()) {
        String sr_no = Integer.toString(rset.getInt("Sr_no"));
        String f_sr_no = format_data(sr_no, 7);
        String company_name = rset.getString("Company_Name");
        String f_company_name = format_data(company_name, 20);
        String company_id = rset.getString("Company_ID");
        String f_company_id = format_data(company_id, 12);
        System.out.println("| " + f_sr_no + " | " + f_company_name + "| " + f_company_id + "  |");
      }
      System.out.println("=================================================");
      rset.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Override
  public void display_med_User() { // Override Interface Function
    try {
      String u_detail = "select * from USERS;";
      Statement smt = con.createStatement();
      ResultSet rset = smt.executeQuery(u_detail);
      System.out.println("=========================================================");
      System.out.println("| Sr_No | Name         | Number       | Address         |");
      System.out.println("|-------------------------------------------------------|");
      while (rset.next()) {
        String sr_no = Integer.toString(rset.getInt("Sr_no"));
        String f_sr_no = format_data(sr_no, 5);
        String number = Integer.toString(rset.getInt("Sr_no"));
        String f_number = format_data(number, 12);
        String name = rset.getString("Name");
        String f_name = format_data(name, 12);
        String address = rset.getString("Address");
        String f_address = format_data(address, 15);
        System.out.println(
            "| " + f_sr_no + " | " + f_name + " | " + f_number + " | " + f_address + " |");
      }
      System.out.println("=========================================================");
      rset.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Override

  void login() { // Override abstract class
    System.out.println("\n:::::::::::::::::::::::::::::::::::");
    System.out.print("|:) Enter Your Name : ");
    owner_name = sc.next();
    System.out.print("|:) Enter Your Password : ");
    owner_pass = sc.next();
    System.out.println(":::::::::::::::::::::::::::::::::::\n");
  }

  void med_details() { // Medicine Detail Function
    System.out.println("|> 1. Display Medicine Detail\n|> 2. Add New Medicine to Store");
    System.out.print("|> Enter Your Choice : ");
    ch = sc.nextInt();
    System.out.println();
    switch (ch) {
      case 1: // Display Medicine Content
        display_med_Det();
        break;
      case 2: // Add New Medicine to Stock
        try {
          display_med_Det();
          String InsertDataQ = "Insert into MEDICINE values(?,?,?,?,?,?)";
          PreparedStatement InsertData = con.prepareStatement(InsertDataQ);
          System.out.println("\n|> Make Sure Serial Number Doesn't Match");
          System.out.print("|> Enter Serial No. : ");
          Sr_no = sc.nextInt();
          System.out.print("|> Enter Medicine name : ");
          med_name = sc.next();
          System.out.print("|> Enter Price : ");
          med_price = sc.nextInt();
          System.out.print("|> Enter Company name : ");
          med_cmp_name = sc.next();
          System.out.print("|> Enter Quantity Number : ");
          med_quantity = sc.nextInt();
          System.out.print("|> Enter Category name : ");
          med_category = sc.next();
          InsertData.setInt(1, Sr_no);
          InsertData.setString(2, med_name);
          InsertData.setInt(3, med_price);
          InsertData.setString(4, med_cmp_name);
          InsertData.setInt(5, med_quantity);
          InsertData.setString(6, med_category);
          InsertData.executeUpdate();
          System.out.println("\nSuccessfully data Inserted");
          InsertData.close();

          display_med_Det();
        } catch (Exception e) {
          System.out.println(e);
        }
        break;
    }
  }

  void supplier_detail() { // All Supplier Detail
    System.out.print("|> 1. Display Company Details\n|> 2. Add Company\n|> 3. Remove Company\n|> Enter Your Choice : ");
    ch = sc.nextInt();
    switch (ch) {
      case 1: // Display Supplier Detail
        display_med_Sup();
        break;
      case 2: // Add New Supplier Detail
        try {
          String addcmp = "insert into Suppliers values(?,?,?)";
          PreparedStatement InsertData = con.prepareStatement(addcmp);
          System.out.print("|> Enter Serial No. : ");
          Sr_no = sc.nextInt();
          System.out.print("|> Enter Company Name : ");
          sup_cmp_name = sc.next();
          System.out.print("|> Enter Company Id : ");
          cmp_id = sc.next();
          InsertData.setInt(1, Sr_no);
          InsertData.setString(2, sup_cmp_name);
          InsertData.setString(3, cmp_id);
          InsertData.executeUpdate();
          System.out.println("\nSuccessfully data Inserted");
          InsertData.close();

          display_med_Sup();
        } catch (Exception e) {
          System.out.println(e);
        }
        break;
      case 3: // Remove Supplier Detail
        try {
          display_med_Sup();
          System.out.print("|> Enter Company Name which you want to delete : ");
          sup_cmp_name = sc.next();
          String delcmp = "delete from Suppliers where Company_Name = ? ";
          PreparedStatement InsertData = con.prepareStatement(delcmp);
          InsertData.setString(1, sup_cmp_name);
          InsertData.executeUpdate();
          InsertData.close();
          display_med_Sup();

        } catch (Exception e) {
          System.out.println(e);
        }
        break;
    }
  }

  void Refill_Item() { // Refilling Stock Function
    try {
      display_med_Det();
      System.out.print("\n|> Enter number of Medicine, you want to Update : ");
      int loop = sc.nextInt();
      for (int i = 0; i < loop; i++) {
        System.out.print("|> Select Medicine by Serial Number : ");
        rf_num[i] = sc.nextInt();
        System.out.print("|> Enter Quantity of selected medicine : ");
        rf_count[i] = sc.nextInt();
        System.out.println();
      }
      int j = 0;
      String getMedPrice = "select * from MEDICINE";
      Statement s = con.createStatement();
      ResultSet r = s.executeQuery(getMedPrice);
      String min_quantity = "update MEDICINE set Quantity=Quantity + ? where Sr_No = ? ";
      PreparedStatement InsertData = con.prepareStatement(min_quantity);
      while (r.next()) {
        int temp = r.getInt("Sr_No");
        if (temp == rf_num[j]) {
          InsertData.setInt(1, rf_count[j]);
          InsertData.setInt(2, rf_num[j]);
          j++;
          InsertData.executeUpdate();
        }
      }
      InsertData.close();
      System.out.println(":::::::::::::::::::::::::::::: After Updating Stock Item ::::::::::::::::::::::::::::::");
      display_med_Det();
    } catch (Exception e) {
          System.out.println("xxxxxxx :> Your data value is too long\nxxxxxxx :> Please enter short data.");
    }
  }
}

class shopping extends shop { // Using Inheritance by extending shop class
  String u_name, u_address;
  String u_number;
  int buy_sr_no[] = new int[10];
  int quantity_num[] = new int[10];
  int amt[] = new int[10];
  int total = 0;

  shopping() { // calling super constructor
    super();
  }

  void userDetail() { // User Detail Function
    System.out.print(
        "+++++++++++++++++++++++++++++++++++++++\n|> 1. Insert User Details\n|> 2. Buying Medicine\n|> 3. Invoice");
    System.out.print("\n+++++++++++++++++++++++++++++++++++++++\n|> Enter Your Choice : ");
    ch = sc.nextInt();
    switch (ch) {
      case 1: // Insert User Detail
        try {
          String u_table = "insert into USERS values(?,?,?,?)";
          PreparedStatement InsertData = con.prepareStatement(u_table);
          System.out.print("\n-------Enter User Details-------\n|> Enter Serial Numner : ");
          Sr_no = sc.nextInt();
          System.out.print("|> Enter Username : ");
          u_name = sc.next();
          System.out.print("|> Enter Phone Number : ");
          u_number = sc.next();
          System.out.print("|> Enter Address(In Short) : ");
          u_address = sc.next();
          InsertData.setInt(1, Sr_no);
          InsertData.setString(2, u_name);
          InsertData.setString(3, u_number);
          InsertData.setString(4, u_address);
          InsertData.executeUpdate();
          System.out.println("\nAfter Successfully Data Inserted");
          InsertData.close();
          display_med_User();
        } catch (Exception e) {
          System.out.println("xxxxxxx :> Your data value is too long\nxxxxxxx :> Please enter short data.");
        }
        break;
      case 2: // Buy Medicine Function
        try {
          display_med_Det();
          System.out.print("| Enter Number of Medicine you want to buy : ");
          int loop = sc.nextInt();
          for (int i = 0; i < loop; i++) {
            System.out.println();
            System.out.print("| Select Medicine Item by Serial Number : ");
            buy_sr_no[i] = sc.nextInt();
            System.out.print("| Enter number of that Medicine : ");
            quantity_num[i] = sc.nextInt();
          }
          int j = 0;
          String getMedPrice = "select * from MEDICINE";
          Statement s = con.createStatement();
          ResultSet r = s.executeQuery(getMedPrice);
          String min_quantity = "update MEDICINE set Quantity=Quantity - ? where Sr_No = ? ";
          PreparedStatement InsertData = con.prepareStatement(min_quantity);
          while (r.next()) {
            int temp = r.getInt("Sr_No");
            int price = r.getInt("Price");
            int quantity = r.getInt("Quantity");
            if(quantity<quantity_num[j]){
              System.out.println("xxxxxx :> You enter more quantity than our stock, which is not possible to sell you\nxxxxxx :> Please Enter less number of medicine than our stock");
              break;
            }
            if (temp == buy_sr_no[j]) {
              amt[j] = price * quantity_num[j];
              total += amt[j];
              InsertData.setInt(1, quantity_num[j]);
              InsertData.setInt(2, buy_sr_no[j]);
              j++;
              InsertData.executeUpdate();
            }
          }
          InsertData.close();
        } catch (Exception e) {
          System.out.println("xxxxxxx :> Your data value is too long\nxxxxxxx :> Please enter short data.");
        }

        break;
      case 3: // Display Bill
        try {
          int i = 0;
          String repeat = " ";
          String f_name = format_data(u_name, 33);
          String totl = Integer.toString(total);
          String f_total = format_data(totl, 6);
          String f_phone_num = format_data(u_number, 55);

          LocalDate dateNow = LocalDate.now();          //date format
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-YYYY");

          System.out.println("====================================================================");
          System.out.println("|                     " + shop_name + "                      |");
          System.out.println("|==================================================================|");
          System.out
              .println("| Client Name : " + f_name + repeat.repeat(0) + "Date : " + dtf.format(dateNow)
                  + " |\n| Number : " + f_phone_num + " |");

          System.out.println("|------------------------------------------------------------------|");
          String getMedName = "select * from MEDICINE";
          Statement st = con.createStatement();
          ResultSet rs = st.executeQuery(getMedName);
          System.out.println("| Sr_No      |         Medicine Name         | Quantity | Price    |");

          while (rs.next()) {
            int temp = rs.getInt("Sr_No");
            String sr_no = Integer.toString(temp);
            String f_sr_no = format_data(sr_no, 10);
            String price = Integer.toString(amt[i]);
            String f_price = format_data(price, 8);
            String quantity = Integer.toString(quantity_num[i]);
            String f_quantity = format_data(quantity, 8);
            String medicine = rs.getString("Medicine_Name");
            String f_medicine = format_data(medicine, 20);
            if (temp == buy_sr_no[i]) {
              System.out.println(
                  "| " + f_sr_no + " | " + repeat.repeat(9) + f_medicine + " | " + f_quantity + " | " + f_price + " |");
              i++;
            }
          }
          System.out.println("|------------------------------------------------------------------|");
          System.out.println("|" + repeat.repeat(51) + "Total : " + f_total + " |");
          System.out.println("====================================================================");

          File f = new File("D:\\e\\VISUAL STUDIO CODE\\JAVA\\Medical\\src\\Project.txt"); // File Handling Concept\\
          if (f.createNewFile()) {
            System.out.println("File created: " + f.getName());
          } else {
            System.out.println("\nFile Already Exists");
          }
          FileWriter fw = new FileWriter("D:\\e\\VISUAL STUDIO CODE\\JAVA\\Medical\\src\\Project.txt");

          fw.write(System.lineSeparator() + "====================================================================");
          fw.write(System.lineSeparator() + "|-------------------- " + shop_name + " ---------------------|");
          fw.write(System.lineSeparator() + "|==================================================================|");
          fw.write(
              System.lineSeparator() + "| Client Name : " + f_name + repeat.repeat(0) + "Date : " + dtf.format(dateNow)
                  + " |\n| Number : " + f_phone_num + " |");

          fw.write(System.lineSeparator() + "|------------------------------------------------------------------|");
          fw.write(System.lineSeparator() + "| Sr_No      |         Medicine Name         | Quantity | Price    |");
          while (rs.next()) {
            int temp = rs.getInt("Sr_No");
            if (temp == buy_sr_no[i]) {
              String sr_no = Integer.toString(temp);
              String f_sr_no = format_data(sr_no, 10);
              String price = Integer.toString(amt[i]);
              String f_price = format_data(price, 8);
              String quantity = Integer.toString(quantity_num[i]);
              String f_quantity = format_data(quantity, 8);
              String medicine = rs.getString("Medicine_Name");
              String f_medicine = format_data(medicine, 20);
              fw.write(System.lineSeparator() +
                  "| " + f_sr_no + " | " + repeat.repeat(9) + f_medicine + " | " + f_quantity + " | " + f_price + " |");
              i++;
            }

          }
          fw.write(System.lineSeparator() + "|------------------------------------------------------------------|");
          fw.write(System.lineSeparator() + "|" + repeat.repeat(51) + "Total : " + f_total + " |");
          fw.write(System.lineSeparator() + "====================================================================");
          fw.close();

        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        } catch (Exception ex) {
          System.out.println(ex);
        }
        break;
    }
  }
}

public class Medical { // Main Class

  public static void main(String args[]) {

    int choice;
    boolean cond = true;
    Scanner m_sc = new Scanner(System.in);
    shopping s = new shopping();
    s.login();
    if (s.owner_name.equals("nitish") && s.owner_pass.equals("qwerty")) { // Verifying Owner Detail
      while (cond) {
        System.out
            .print(
                "|> 1. Medicine Detail\n|> 2. Supplier Detail\n|> 3. User Details\n|> 4. Refilling Stock\n|> Enter Your Choice : ");
        choice = m_sc.nextInt();
        System.out.println();
        switch (choice) {
          case 1:
            s.med_details();    //Calling Medicine Function
            break;
          case 2:
            s.supplier_detail();    //Calling Supplier Function
            break;
          case 3:
            s.userDetail();    //Calling User Detail Function
            break;
          case 4:
            s.Refill_Item();    //Calling Refilling Function
        }
        System.out.print("\n****** Continue ? ******\n|> Enter True/False : "); //Condition for Continue Program
        cond = m_sc.nextBoolean();
        System.out.println();
      }
    } else {
      System.out.println("Wrong User Name Or Password !!!!!!!!\nCheck it and Try Again");
    }
    System.out.println("Thank You For visiting my Shop\nSee you Next Time");
    m_sc.close();
    try {
      s.con.close();
    } catch (Exception e) {
      System.out.println("You'r left the medical shop unsually or might be possible you enter worng input\nRe-Try");
    }
  }
}