// OffloadingInterface.aidl
package com.example.clientapp;

// Declare any non-default types here with import statements

interface OffloadingInterface {

   int status();
   boolean get_status_connected();
   String offload(String data);
}