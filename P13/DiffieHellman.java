import java.util.*;
class DiffieHellman
{
public static void main(String[] args)
{
Scanner sc=new Scanner(System.in);
System.out.println("Enter modulo(p)");
int p=sc.nextInt();
System.out.println("Enter primitive root of "+p);
int g=sc.nextInt();
System.out.println("Enter 1st secret number(1st Person)");
int a=sc.nextInt();
System.out.println("Enter 2nd secret number(2nd Person)");
int b=sc.nextInt();
int A = (int)Math.pow(g,a)%p;
int B = (int)Math.pow(g,b)%p;
int S_A = (int)Math.pow(B,a)%p;
int S_B =(int)Math.pow(A,b)%p;
if(S_A==S_B)
{
System.out.println("1st Person and 2nd Person can communicate with each other!!!");
System.out.println("They share a secret number = "+S_A);}
else
{
System.out.println("1st Person and 2nd Person cannot communicate with each other!!!");
}
}
}
