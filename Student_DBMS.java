/** TO DO ---->
PACK ALL THE LOCAL AND REPEATED METHODS IN FUNCTIONS.
**/
import java.util.*;
import java.io.*;
public class Student_DBMS
{
    static String timings[]=new String[10],Name,Address,Day1,Day2;
    static String local[]=new String[2];
    static String temp_time[]=new String[2];
    static int i=0,x=-1,j;
    static long Phone,id,Class;
    static String ptr="";
    static boolean flag2;
    static void local_init()
    {
        for(int p=0;p<timings.length;p++)
            timings[x]="";
        i=0;
    }

    static void init()
    {
        for(int p=0;p<10;p++)
            timings[p]="";
        for(int p=0;p<2;p++)
            temp_time[p]="";
        x=-1;
        j=0;
        Name=Address=Day1=Day2=ptr="";
        Phone=id=Class=0;
        flag2=true;
        i=0;
    }

    static void add()throws IOException
    {
        Scanner sc=new Scanner(System.in);
        int ch;
        for(j=1;j<=2;j++)
        {
            if(j==1)   
            {
                System.out.println("Enter the day for the 1st class : ");
                avail_day();   
                while(true)
                {
                    ch=sc.nextInt();
                    if(ch>=1 && ch<=7)    
                    {
                        call_display_timing(ch); 
                        break;    
                    }
                    else 
                        System.out.println("Please enter a valid input"+"\n"+"Please try again!!!");
                }   
            }
            else    
            {
                System.out.println("Enter the day for the 2nd class : ");
                System.out.println("Or press 0 to skip");
                avail_day();
                while(true)   
                {
                    ch=sc.nextInt();    
                    if(ch>=1 && ch<=7)
                    {
                        local_init();   
                        call_display_timing(ch);
                        break;   
                    }
                    else   
                    if(ch==0)
                        break;   
                    else
                        System.out.println("Please enter a valid input"+"\n"+"Please try again!!!");
                }
                if(ch==0)
                { 
                    temp_time[++x]="";  
                    Day2=""; 
                    if(ptr.equals("Editing")==false) 
                        accept_student_details();  
                    break;
                }
            }   
        }  
    }   

    static void call_display_timing(int x)throws IOException   
    {
        if(x==1)
            display_timing("SATURDAY.txt","SATURDAY");
        if(x==2)
            display_timing("SUNDAY.txt","SUNDAY");
        if(x==3)
            display_timing("MONDAY.txt","MONDAY");
        if(x==4)
            display_timing("TUESDAY.txt","TUESDAY");
        if(x==5)
            display_timing("WEDNESDAY.txt","WEDNESDAY");
        if(x==6)
            display_timing("THRUSDAY.txt","THRUSDAY");
        if(x==7)
            display_timing("FRIDAY.txt","FRIDAY");
    }

    static void display_timing(String day,String d)throws IOException    
    {
        String path,print;
        path="C:Users/Destro/Desktop/DataBase/TIMINGS/"+day;
        BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
        FileReader fr=new FileReader(path);
        BufferedReader br2=new BufferedReader(fr);
        System.out.println("The Timings of "+d+" are as follows : ");
        while((print=br2.readLine())!=null)
        {
            if(check_availability(print,d)==true)
            {
                timings[i]=print; 
                System.out.println((++i)+"] "+print);
            }
        }
        br2.close();
        fr.close();
        if(j==1)
        {
            Day1=d;
            accept_timing();
        }
        else
        {
            Day2=d;
            accept_timing();
        }
    }

    static void accept_timing()throws IOException
    {
        Scanner sc1=new Scanner(System.in);
        int ch;
        while(true)
        {
            System.out.println("Please select one of your desired time : ");
            ch=sc1.nextInt();
            if(ch<=i)
                break;
            else
                System.out.println("Invalid Input!!"+"\n"+"Please Try Again!!!");
        }
        temp_time[++x]=timings[--ch];
        System.out.println("Time selected successfully!!!!!");
        if(j==2 && ptr.equals("Editing")==false)
            accept_student_details();
    }

    static void update_main_DataBase(long unique,String nam,long cls,String addr,long phn,String d1,String d2,String temp_time[])throws IOException
    {
        BufferedReader obj4=new BufferedReader(new InputStreamReader(System.in));
        FileOutputStream obj5=new FileOutputStream("C:/Users/Destro/Desktop/DataBase/Main DataBase.dat",true);
        DataOutputStream obj6=new DataOutputStream(obj5);
        obj6.writeLong(unique);
        obj6.writeUTF(nam);
        obj6.writeLong(cls);
        obj6.writeUTF(addr);
        obj6.writeLong(phn);
        obj6.writeUTF(d1);
        obj6.writeUTF(temp_time[0]);
        obj6.writeUTF(d2);
        obj6.writeUTF(temp_time[1]);
        System.out.println("DataBase Updated Successfully!!!!!");
        obj5.close();
        obj6.close();
    }

    static void accept_student_details()throws IOException
    {
        Scanner sc2=new Scanner(System.in);
        Scanner sc3=new Scanner(System.in);
        System.out.println();
        System.out.println("Kindly enter the details of the student");
        while(true)
        {
            System.out.println("Enter the UID of the student"); 
            id=sc2.nextLong();
            if(validate_UID(id)==true)
                break;
            else
                System.out.println("The Unique ID is invalid or already used up."+"/n"+"Please re-enter the UID");
        }
        System.out.println("Enter the name of the student : ");
        Name=sc3.nextLine();
        System.out.println("Enter the class of the student : ");
        Class=sc2.nextLong();
        System.out.println("Enter the address of the student : ");
        Address=sc3.nextLine();
        System.out.println("Enter the phone number of the student : ");
        Phone=sc2.nextLong();
        if(j==2)
            update_main_DataBase(id,Name,Class,Address,Phone,Day1,Day2,temp_time);
    }

    static boolean validate_UID(long x)throws IOException
    {
        BufferedReader obj1=new BufferedReader(new InputStreamReader(System.in));
        FileInputStream obj2=new FileInputStream("C:/Users/Destro/Desktop/DataBase/Main DataBase.dat");
        DataInputStream obj3=new DataInputStream(obj2);
        boolean eof=false;
        String name,address,day1,time1,day2,time2;
        long uid,phone,std;
        int k=0;
        while(!eof)
        {
            try
            {
                uid=obj3.readLong();   
                name=obj3.readUTF();   
                std=obj3.readLong();
                address=obj3.readUTF();   
                phone=obj3.readLong();    
                day1=obj3.readUTF();    
                time1=obj3.readUTF();    
                day2=obj3.readUTF();   
                time2=obj3.readUTF(); 
                if(uid==x)
                {
                    k=1;
                    break;
                }
            }
            catch(EOFException e)
            {   
                eof=true;
            }
        }
        obj2.close();
        obj3.close();
        if(k==1)
            return false;
        else
            return true;

    }

    static boolean check_availability(String time,String day)throws IOException
    {
        BufferedReader obj7=new BufferedReader(new InputStreamReader(System.in));
        FileInputStream obj8=new FileInputStream("C:/Users/Destro/Desktop/DataBase/Main DataBase.dat");
        DataInputStream obj9=new DataInputStream(obj8);
        boolean eof=false;
        String name,address,day1,time1,day2,time2;
        long uid,phone,std;
        int k=0;
        while(!eof)
        {
            try
            {
                uid=obj9.readLong();   
                name=obj9.readUTF(); 
                std=obj9.readLong();
                address=obj9.readUTF();   
                phone=obj9.readLong();   
                day1=obj9.readUTF();    
                time1=obj9.readUTF();   
                day2=obj9.readUTF();   
                time2=obj9.readUTF();   
                if(day1.equals(day)==true && time1.equals(time)==true)
                    k++;
                else 
                if(day2.equals(day)==true && time2.equals(time)==true)
                    k++;
            }
            catch(EOFException e)
            {
                eof=true;
            }
        }
        obj8.close();
        obj9.close();
        if(k<=11)
            return true;
        else
            return false;
    }

    static void display_database()throws IOException
    {
        BufferedReader obj10=new BufferedReader(new InputStreamReader(System.in));
        FileInputStream obj11=new FileInputStream("C:/Users/Destro/Desktop/DataBase/Main DataBase.dat");
        DataInputStream obj12=new DataInputStream(obj11);
        boolean eof=false;
        String name,address,day1,time1,day2,time2;
        long uid,phone,std;
        int k=0;
        while(!eof)
        {
            try
            {
                uid=obj12.readLong();
                name=obj12.readUTF();   
                std=obj12.readLong();
                address=obj12.readUTF(); 
                phone=obj12.readLong();   
                day1=obj12.readUTF();    
                time1=obj12.readUTF();   
                day2=obj12.readUTF();   
                time2=obj12.readUTF();   
                System.out.println(uid+"\t"+name+"\t\t"+std+"\t"+address+"\t\t\t"+phone+"\t"+day1+"\t\t"+time1+"\t\t"+day2+"\t\t"+time2);
            }
            catch(EOFException e)
            {
                eof=true;
            }
        }
        obj11.close();
        obj12.close();
    }

    static void avail_day()
    {
        System.out.println("1] SATURDAY");
        System.out.println("2] SUNDAY");
        System.out.println("3] MONDAY");
        System.out.println("4] TUESDAY");
        System.out.println("5] WEDNESDAY");
        System.out.println("6] THRUSDAY");
        System.out.println("7] FRIDAY");
    }

    static void delete(long UID,String str)throws IOException
    {
        Scanner sc=new Scanner(System.in);
        BufferedReader obj13=new BufferedReader(new InputStreamReader(System.in));
        FileInputStream obj14=new FileInputStream("C:/Users/Destro/Desktop/DataBase/Main DataBase.dat");
        DataInputStream obj15=new DataInputStream(obj14);
        FileOutputStream obj16=new FileOutputStream("C:/Users/Destro/Desktop/DataBase/Temp.dat");
        DataOutputStream obj17=new DataOutputStream(obj16);
        boolean eof=false,uid_pres=false;
        String name,address,day1,time1,day2,time2;
        long uid,phone,std;
        int ch;
        while(!eof)
        {
            try
            {
                uid=obj15.readLong();   
                name=obj15.readUTF();  
                std=obj15.readLong();
                address=obj15.readUTF(); 
                phone=obj15.readLong();   
                day1=obj15.readUTF();    
                time1=obj15.readUTF();  
                day2=obj15.readUTF();   
                time2=obj15.readUTF();  
                if(UID==uid)
                {
                    if(str.equals("PROMPT")==false)
                    {
                        System.out.println("Student Found!!!");
                        System.out.println("Name :-"+name);
                        System.out.println("Class :-"+std);
                        System.out.println("Address :-"+address);
                        System.out.println("Phone Number :-"+phone);
                        System.out.println();
                        System.out.println("Press 1 to confirm deletion");
                        System.out.println("Press 0 to cancel deletion");
                        ch=sc.nextInt();
                    }
                    else
                        ch=1;
                    if(ch==1 && str.equals("PROMPT")==false)   
                        System.out.println("The database of "+name+" is deleted successfully");
                    else
                    if(ch==1 && str.equals("PROMPT")==true)
                        System.out.println("Previous record of the student is successfully deleted");
                    else
                    if(ch==0 && str.equals("PROMPT")==false)
                    {
                        System.out.println("Process terminated");
                        obj17.writeLong(uid);   
                        obj17.writeUTF(name); 
                        obj17.writeLong(std);
                        obj17.writeUTF(address);  
                        obj17.writeLong(phone);    
                        obj17.writeUTF(day1);    
                        obj17.writeUTF(time1);   
                        obj17.writeUTF(day2);   
                        obj17.writeUTF(time2);   
                    }
                }     //if block closed

                else   
                {
                    obj17.writeLong(uid);   
                    obj17.writeUTF(name);   
                    obj17.writeLong(std);
                    obj17.writeUTF(address);  
                    obj17.writeLong(phone);   
                    obj17.writeUTF(day1);    
                    obj17.writeUTF(time1);   
                    obj17.writeUTF(day2);   
                    obj17.writeUTF(time2);   
                }
            }  // try block ending
            catch(EOFException e)
            {
                eof=true;
                if(uid_pres=false)
                    System.out.println("UID Not Present");
            }
        }
        obj15.close();
        obj17.close();
        File f1=new File("C:/Users/Destro/Desktop/DataBase/Main DataBase.dat");
        f1.delete();
        File f2=new File("C:/Users/Destro/Desktop/DataBase/Temp.dat");
        boolean f=f2.renameTo(f1);
        if(!f)
            System.out.println("Renaming not successfull");
        else
            System.out.println("Rename Success");
    }

    static void search()throws IOException
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the UID");
        long unik=sc.nextLong();
        BufferedReader obj18=new BufferedReader(new InputStreamReader(System.in));
        FileInputStream obj19=new FileInputStream("C:/Users/Destro/Desktop/DataBase/Main DataBase.dat");
        DataInputStream obj20=new DataInputStream(obj19);
        boolean eof=false;
        String name,address,day1,time1,day2,time2;
        long uid,phone,std;
        int k=0;
        while(!eof)
        {
            try
            {
                uid=obj20.readLong();   
                name=obj20.readUTF();   
                std=obj20.readLong();
                address=obj20.readUTF();  
                phone=obj20.readLong();   
                day1=obj20.readUTF();  
                time1=obj20.readUTF();  
                day2=obj20.readUTF();
                time2=obj20.readUTF();
                if(uid==unik)
                {
                    System.out.println("Name of the student  : "+name);
                    System.out.println("Class                : "+std);
                    System.out.println("Address              : "+address);
                    System.out.println("Phone Number         : "+phone);
                    System.out.println("Day for 1st class    : "+day1);
                    System.out.println("Timing for 1st class : "+time1);
                    System.out.println("Day for 2nd class    : "+day2);
                    System.out.println("Timing for 2nd class : "+time2);
                    break;
                }
            }
            catch(EOFException e)
            {
                eof=true;
            }
        }
        obj19.close();
        obj20.close();
    }

    static void edit() throws IOException
    {
        Scanner sc=new Scanner(System.in);
        Scanner sp=new Scanner(System.in);
        System.out.println("Enter the UID Of the student");
        long uID=sc.nextLong();
        BufferedReader obj21=new BufferedReader(new InputStreamReader(System.in));
        FileInputStream obj22=new FileInputStream("C:/Users/Destro/Desktop/DataBase/Main DataBase.dat");
        DataInputStream obj23=new DataInputStream(obj22);
        boolean eof=false;
        String name,address,day1,time1,day2,time2;
        long uid,phone,std;
        int k=0,ch,kill=0;
        boolean uid_pres=true;
        while(!eof)
        {
            try
            {
                uid=obj23.readLong();   
                name=obj23.readUTF();   
                std=obj23.readLong();
                address=obj23.readUTF();  
                phone=obj23.readLong();   
                day1=obj23.readUTF();  
                time1=obj23.readUTF();  
                day2=obj23.readUTF();   
                time2=obj23.readUTF();  
                if(uID==uid)
                {
                    System.out.println();
                    System.out.println("Student Found");
                    System.out.println("Name of the student  : "+name);
                    System.out.println("Class                : "+std);
                    System.out.println("Address              : "+address);
                    System.out.println("Phone Number         : "+phone);
                    System.out.println("Day for 1st class    : "+day1);
                    System.out.println("Timing for 1st class : "+time1);
                    System.out.println("Day for 2nd class    : "+day2);
                    System.out.println("Timing for 2nd class : "+time2);
                    String new_name="",new_address="";
                    long new_class=0,new_number=0;
                    kill=1;
                    while(true)
                    {
                        System.out.println("Press 1 to edit the NAME");
                        System.out.println("Press 2 to edit the CLASS");
                        System.out.println("Press 3 to edit the ADDRESS");
                        System.out.println("Press 4 to edit the PHONE NUMBER");
                        System.out.println("Press 5 to edit the TIMINGS");
                        System.out.println("Press 6 to SAVE CHANGES");
                        System.out.println("Enter your choice : ");
                        ch=sc.nextInt();

                        if(ch==1)
                        {
                            System.out.println("Enter the new name : ");
                            new_name=sp.nextLine();
                            System.out.println("The name accepted successfully");
                        }
                        else
                        if(ch==2)
                        {
                            System.out.println("Enter the new class : ");
                            new_class=sc.nextLong();
                            System.out.println("The class accepted successfully");
                        }
                        else
                        if(ch==3)
                        {
                            System.out.println("Enter the new address : ");
                            new_address=sp.nextLine();
                            System.out.println("The address accepted successfully");
                        }
                        else
                        if(ch==4)
                        {
                            System.out.println("Enter the new phone number : ");
                            new_number=sc.nextLong();
                            System.out.println("The phone number accepted successfully");
                        }
                        else
                        if(ch==5)
                        {
                            flag2=true;
                            ptr="Editing";
                            add();
                        }
                        else
                        if(ch==6)
                        {
                            if(new_name=="")
                                new_name=name;
                            if(new_class==0)
                                new_class=std;
                            if(new_address=="")
                                new_address=address;
                            if(new_number==0)
                                new_number=phone;
                            if(flag2==true)
                            {
                                obj22.close();
                                obj23.close();
                                delete(uID,"PROMPT");
                                update_main_DataBase(uID,new_name,new_class,new_address,new_number,Day1,Day2,temp_time);
                            }
                            else
                            {
                                obj22.close();
                                obj23.close();
                                delete(uID,"PROMPT");
                                local[0]=time1;
                                local[2]=time2;
                                update_main_DataBase(uID,new_name,new_class,new_address,new_number,day1,day2,local);
                            }
                            break;
                        }
                        else             
                            System.out.println("Invalid Input!!!!"+"\n"+"Please try again");
                    }
                }
                else
                    continue;
            }
            catch(EOFException e)
            {
                eof=true;
                if(uid_pres==false)
                    System.out.println("The entered uid is not present in the database");
            }
            if(kill==1)
                break;
        }
        obj22.close();
        obj23.close();
    }

    static void display_developer_profile()
    {
        System.out.println("THANKS FOR USING OUR APPLICATION");
        System.out.println("Author : Gaurav Aggarwal");
        System.out.println("Date Created : 30-04-2017");

    }

    public static void main(String args[])throws IOException
    {
        int ch;
        Scanner sc=new Scanner(System.in);
        Scanner sp=new Scanner(System.in);
        while(true)
        {
            System.out.println("Press 1 to ADD a record");
            System.out.println("Press 2 to DELETE a record");
            System.out.println("Press 3 to EDIT a record");
            System.out.println("Press 4 to SEARCH for a record");
            System.out.println("Press 5 to DISPLAY THE MAIN DATABASE");
            System.out.println("Enter your choice");
            ch=sc.nextInt();
            switch(ch)
            {
                case 1:
                add();
                break;
                case 2:
                System.out.println("Enter the UID of the student");
                long uId=sc.nextLong();
                delete(uId,"nprmpt");
                break;
                case 3:
                edit();
                break;
                case 4:
                search();
                break;
                case 5:
                display_database();
                break;
                default:
                System.out.println("Invalid Input!!!!!");
            }
            System.out.println("Do you need to manage more records");
            String str1=sp.nextLine();
            if(str1.equalsIgnoreCase("yes") || str1.equals("1")==true || str1.equalsIgnoreCase("Y"))
            {
                init();
                System.out.print('\u000C');
            }
            else
            {
                display_developer_profile();
                break;
            }
        }
    }
}
