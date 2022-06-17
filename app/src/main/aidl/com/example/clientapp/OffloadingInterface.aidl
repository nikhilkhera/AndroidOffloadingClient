// OffloadingInterface.aidl
package com.example.clientapp;

// Declare any non-default types here with import statements

interface OffloadingInterface {

   int status();
   boolean get_status_connected();
   void offload(String data);
   boolean get_response_result();
   void set_response_result_false();
   String get_response_data();
}