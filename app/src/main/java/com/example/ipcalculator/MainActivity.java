package com.example.ipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Spinner mask_Bit_Range;
    TextView ip, host_per_subnet, subnetMask,IP_Binary_Netmask;
    TextView max_mubnets, Network_Address, IP_Binary;
    int n1 = 0, n2 = 0, n3 = 0, n4 = 0, flag = 0, max_Subnets;
    int subnet1 = 0, subnet2 = 0,subnet3 = 0, j = 0, k = 0,start = 0;
    int array[] = new int[]{128,64,32,16,8,4,2,1};
    int rj1,rj2,rj3,len1 = 0, len2 = 0, len3 = 0, len4 = 0;
    String x1,x2,x3,x4,str1="",str2="",str3="",str4="";
    int range1 = 0, range2 = 0,range3 = 0,temp1 = 1, temp2 = 1,temp3 = 1;
    int sb1 = 0,sb2 = 0,sb3 = 0,sb4 = 0;
    String ip_address[],subnetmask[];
    int mask_bits;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mask_Bit_Range = findViewById(R.id.spinner1);
        ip = findViewById(R.id.ip);
        button = findViewById(R.id.button);
        max_mubnets = findViewById(R.id.max_subnets);
        host_per_subnet = findViewById(R.id.host_per_subnet);
        subnetMask = findViewById(R.id.subnet_mask);
        Network_Address = findViewById(R.id.network_address);
        IP_Binary = findViewById(R.id.ip_binary);
        IP_Binary_Netmask = findViewById(R.id.ip_binary_netmask);

        ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ip_address = ip.getText().toString().split("\\.");

                n1 = Integer.parseInt(ip_address[0]);
                n2 = Integer.parseInt(ip_address[1]);
                n3 = Integer.parseInt(ip_address[2]);
                n4 = Integer.parseInt(ip_address[3]);

                if (n1 >= 0 && n1 <= 127) {
                    String items[] = new String[]{"8","9","10","11","12","13","14","15","16","17","18","19","20",
                            "21","22","23","24","25","26","27","28","29","30"};
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                    mask_Bit_Range.setAdapter(adapter);
                }
                if (n1 >= 128 && n1 <= 191) {
                    String items[] = new String[]{"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                    mask_Bit_Range.setAdapter(adapter);
                }
                if(n1 >= 192 && n1 <=223){
                    String items[] = new String[]{"24","25","26","27","28","29","30"};
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                    mask_Bit_Range.setAdapter(adapter);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    ip_address = ip.getText().toString().split("\\.");

                    n1 = Integer.parseInt(ip_address[0]);
                    n2 = Integer.parseInt(ip_address[1]);
                    n3 = Integer.parseInt(ip_address[2]);
                    n4 = Integer.parseInt(ip_address[3]);

                    if (n1 >= 0 && n1 <= 127) {

                        // Calculate Maximum subnets....
                        mask_bits = Integer.parseInt(mask_Bit_Range.getSelectedItem().toString());
                        max_Subnets = 32 - mask_bits;
                        host_per_subnet.setText(String.valueOf(Math.pow(2,max_Subnets)-2)); //set host per subnet
                        max_Subnets = 24 - max_Subnets;

                        for(int i = 0; i < max_Subnets; i++) {
                            if(i < 8)
                                subnet1 += array[i];
                            else if(i >= 8 && i < 16){              //Calculating subnet mask.....
                                subnet2 += array[j];
                                j++;
                            }
                            else if(i >= 16 && i < 24){
                                subnet3 += array[k];
                                k++;
                            }
                        }
                        subnetMask.setText("255."+String.valueOf(subnet1)+"."+
                                String.valueOf(subnet2)+"."+String.valueOf(subnet3));

                        //Network Address calculation...

                        for(int i = 1; i<= max_Subnets; i++)
                        {
                            if(i >= 1 && i <= 8)
                                temp1 = temp1 * 2;
                            else if(i >= 9 && i <= 16)
                                temp2 = temp2 * 2;
                            else if(i >= 17 && i <= 24)
                                temp3 = temp3 * 2;
                        }

                        range1 = 256 / temp1;
                        range2 = 256 / temp2;
                        range3 = 256 / temp3;

                        for(start = 1; start <= max_Subnets; start++)
                        {
                            if(start <= 8)
                            {
                                for(int var1 = 0; var1 < 256; var1 = var1 + range1)
                                {
                                    if(n2 >= var1 && n2 <= var1 + range1)
                                        rj1 = var1;
                                }
                            }
                            else if(start <= 16)
                            {
                                for(int var2 = 0; var2 < 256; var2 = var2 + range2)
                                {
                                    if(n3 >= var2 && n3 <= var2 + range2)
                                        rj2 = var2;
                                }
                            }
                            else if(start <=24) {
                                for(int var3 = 0; var3 < 256; var3 = var3 + range3)
                                {
                                    if(n4 >= var3 && n4 <= var3 + range3)
                                        rj3 = var3;
                                }
                            }
                        }

                        Network_Address.setText(String.valueOf(n1)+"."+String.valueOf(rj1)+"."+
                                String.valueOf(rj2)+"."+String.valueOf(rj3));
                        temp1 = temp2 = temp3 = 1;
                        range1 = range2 = range3 = 0;
                        rj1 = rj2 = rj3 = 0;

                        max_Subnets = (int) Math.pow(2,max_Subnets);
                        max_mubnets.setText(String.valueOf("")+max_Subnets);

                    }
                    if (n1 >= 128 && n1 <= 191) {

                        // Calculate Maximum subnets....
                        mask_bits = Integer.parseInt(mask_Bit_Range.getSelectedItem().toString());
                        max_Subnets = 32 - mask_bits;
                        host_per_subnet.setText(String.valueOf(Math.pow(2,max_Subnets)-2)); //set host per subnet
                        max_Subnets = 16 - max_Subnets;

                        for(int i = 0; i < max_Subnets; i++) {
                            if(i < 8)                           //Calculating subnet mask.....
                                subnet1 += array[i];
                            else if(i >= 8 && i < 16){
                                subnet2 += array[j];
                                j++;
                            }
                        }
                        subnetMask.setText("255."+"255."+ String.valueOf(subnet1)+
                                "."+String.valueOf(subnet2));

                        //Network Address calculation...

                        for(int i=1;i<=max_Subnets;i++)
                        {
                            if(i>=1 && i<=8)
                                temp1=temp1*2;
                            else if(i>=9 && i<=16)
                                temp2=temp2*2;
                        }

                        range1 = 256/temp1;
                        range2 = 256/temp2;

                        for(start=1;start<=max_Subnets;start++)
                        {
                            if(start<=8)
                            {
                                for(int var1=0;var1<256;var1=var1+range1)
                                {
                                    if(n3>=var1 && n3<=var1+range1)
                                        rj1=var1;
                                }
                            }
                            else if(start<=16)
                            {
                                for(int var2=0;var2<256;var2=var2+range2)
                                {
                                    if(n4>=var2 && n4<=var2+range2)
                                        rj2=var2;
                                }
                            }
                        }

                        Network_Address.setText(String.valueOf(n1)+"."+String.valueOf(n2)+"."+
                                String.valueOf(rj1)+"."+String.valueOf(rj2));
                        temp1 = temp2 = 1;
                        range1 = range2 = 0;
                        rj1 = rj2 = 0;


                        max_Subnets = (int) Math.pow(2,max_Subnets);
                        max_mubnets.setText(String.valueOf("")+max_Subnets);
                    }
                    if(n1 >= 192 && n1 <=223){

                        // Calculate Maximum subnets....
                        mask_bits = Integer.parseInt(mask_Bit_Range.getSelectedItem().toString());
                        max_Subnets = 32 - mask_bits;
                        host_per_subnet.setText(String.valueOf(Math.pow(2,max_Subnets)-2)); //set host per subnet
                        max_Subnets = 8 - max_Subnets;

                        for(int i = 0; i < max_Subnets; i++) {
                            subnet1 += array[i];            //Calculating subnet mask.....
                        }
                        subnetMask.setText("255."+"255."+"255."+String.valueOf(subnet1));

                        //Network Address calculation...
                        range1 = (int) (256 / (Math.pow(2,max_Subnets)));
                        for(int i = 0; i < 256; ) {
                            if(n4>=i && n4 <= i+range1) {
                                Network_Address.setText(String.valueOf(n1)+"."+String.valueOf(n2)+"."+
                                        String.valueOf(n3)+"."+String.valueOf(i));
                                break;
                            }
                            i += range1;
                        }

                        max_Subnets = (int) Math.pow(2,max_Subnets);
                        max_mubnets.setText(String.valueOf("")+max_Subnets);
                    }
                    j = k = subnet1 = subnet2 = subnet3 = 0;

                    //IP Binary Conversion
                    x1 = Integer.toBinaryString(n1);
                    x2 = Integer.toBinaryString(n2);
                    x3 = Integer.toBinaryString(n3);
                    x4 = Integer.toBinaryString(n4);

                    len1 = 8 - x1.length();
                    len2 = 8 - x2.length();
                    len3 = 8 - x3.length();
                    len4 = 8 - x4.length();
                    for(int i=0;i<len1;i++){
                        str1 = str1 + "0";
                    }
                    for(int i=0;i<len2;i++){
                        str2 = str2 + "0";
                    }
                    for(int i=0;i<len3;i++){
                        str3 = str3 + "0";
                    }
                    for(int i=0;i<len4;i++){
                        str4 = str4 + "0";
                    }
                    IP_Binary.setText(str1+x1+"."+str2+x2+"."+str3+x3+"."+str4+x4);
                    str1=str2=str3=str4="";

                    //IP Binary Netmask...
                    subnetmask = subnetMask.getText().toString().split("\\.");

                    sb1 = Integer.parseInt(subnetmask[0]);
                    sb2 = Integer.parseInt(subnetmask[1]);
                    sb3 = Integer.parseInt(subnetmask[2]);
                    sb4 = Integer.parseInt(subnetmask[3]);

                    x1 = Integer.toBinaryString(sb1);
                    x2 = Integer.toBinaryString(sb2);
                    x3 = Integer.toBinaryString(sb3);
                    x4 = Integer.toBinaryString(sb4);

                    len1 = 8 - x1.length();
                    len2 = 8 - x2.length();
                    len3 = 8 - x3.length();
                    len4 = 8 - x4.length();

                    for(int i=0;i<len1;i++){
                        str1 = str1 + "0";
                    }
                    for(int i=0;i<len2;i++){
                        str2 = str2 + "0";
                    }
                    for(int i=0;i<len3;i++){
                        str3 = str3 + "0";
                    }
                    for(int i=0;i<len4;i++){
                        str4 = str4 + "0";
                    }
                    IP_Binary_Netmask.setText(str1+x1+"."+str2+x2+"."+str3+x3+"."+str4+x4);
                    str1=str2=str3=str4="";
                }
                catch (Exception e){
                    //Toast.makeText(MainActivity.this,"Please Select Mask Bits", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}