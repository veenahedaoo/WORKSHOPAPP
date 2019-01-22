package com.tantransh.workshopapp.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tantransh.workshopapp.appdata.AppPreferences;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.jobbooking.data.CustomerInformation;
import com.tantransh.workshopapp.jobbooking.data.VehicleInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ServiceDispatcher implements RequestListener{
    private RequestQueue queue;
    @SuppressLint("StaticFieldLeak")
    private static  ServiceDispatcher instance;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private ImageLoader imageLoader;

    private ServiceDispatcher(Context context){
        ServiceDispatcher.context = context;
        this.queue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String,Bitmap> cache = new LruCache<>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }

    public static ServiceDispatcher getInstance(Context context){
        if(instance==null) {
            instance = new ServiceDispatcher(context);
        }
        return instance;
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }

    /**
     * method name : login
     * @param userId = user_id (mobile number which is used to get login);
     * @param password = password for authentication;
     * @param listener = response listener to handle server response;
     * @param errorListener = error listener to handle errors;
     * sends request to server to logged in and collects the server response
     */
    @Override
    public void login(final String userId, final String password, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        JSONObject json = new JSONObject();
        try {
            json.put("user_id",userId);
            json.put("password",password);
            json.put("request","login");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(ServiceData.BASEURL+ServiceData.AUTHURL);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.AUTHURL,json,listener,errorListener){



            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println("Response : "+res);
                    JSONObject resJSON = new JSONObject(res);

                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    /**
     * methodname : searchVehicle
     * @param vehicleId vehicle registration number
     * @param listener response listener
     * @param errorListener error listener
     *
     */
    @Override
    public void searchVehicle(String vehicleId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        vehicleId = vehicleId.replace(" ","%20");
        JSONObject reqJson = new JSONObject();
        try {
            reqJson.put("vehicle_no",vehicleId);
            reqJson.put("request","vehicledetails");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.DATAURL,reqJson,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }


    /**
     *
     * @param customerInformation customer information class which holds all details related to customer
     * @param listener response listener
     * @param errorListener error listener
     */
    @Override
    public void addCustomer(CustomerInformation customerInformation, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(customerInformation);
        JSONObject json;

        try {
            json = new JSONObject(jsonString);

            json.put("request","add_customer");
            json.put("vehicle_no",BookingDetails.getInstance().getVehicleRegNo());

            System.out.println("JSON: "+json);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.DATAURL,json,listener,errorListener){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String res = new String(response.data,"UTF-8");
                        System.out.println(res);


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    return super.parseNetworkResponse(response);
                }
            };

            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param vehicleInformation vehicle information object which has all details related vehcile
     * @param listener response listener
     * @param errorListener error listener
     * @throws JSONException json exception
     */
    @Override
    public void registerVehicle(VehicleInformation vehicleInformation, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) throws JSONException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(vehicleInformation);
        JSONObject requestJSON = new JSONObject(jsonString);
        requestJSON.put("request","add_vehicle");
        System.out.println(requestJSON);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.DATAURL,requestJSON,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);
                    JSONObject resJSON = new JSONObject(res);

                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }


    /**
     *
     * @param listener response listener
     * @param errorListener error listener
     */

    @Override
    public void getState(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){

        JSONObject json = new JSONObject();
        try {
            json.put("request","state_list");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.CONTENTSERVICE,json,listener,errorListener);

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(request);
    }

    /**
     *
     * @param listener response listener
     * @param errorListener error listener
     */
    @Override
    public void getMake(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JSONObject reqJSON = new JSONObject();
        try {
            reqJSON.put("request","get_vehicle_make");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.CONTENTSERVICE,reqJSON,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    /**
     *
     * @param listener response listener
     * @param errorListener error listener
     */
    @Override
    public void getCurrentJobs(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        JSONObject json = new JSONObject();
        try {
            json.put("request","get_current_jobs");
            json.put("user_id",AppPreferences.getInstance(context).getUserId());
            json.put("status",1);
            json.put("from_date",Validator.getCurrentDate());
            json.put("to_date",Validator.getCurrentDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.JOBSERVICE,json,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    @Override
    public void getOpenJobs(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JSONObject json = new JSONObject();
        try {
            json.put("request","open_jobs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.JOBSERVICE,json,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    /**
     *
     * @param jobId booking_id
     */

    @Override
    public void getJobInformation(String jobId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        JSONObject json = new JSONObject();
        try {
            json.put("request","detail_job_information");
            json.put("booking_id",jobId);
            System.out.println(json);
            System.out.println(ServiceData.BASEURL+ServiceData.JOBSERVICE);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.JOBSERVICE,json,listener,errorListener);

            queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    System.out.println("request finished");
                }
            });
            queue.add(request);
            System.out.println("request sent : "+queue.getSequenceNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getItemList(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("request","get_items");
            jsonObject.put("category_id",1);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ServiceData.BASEURL_2+ServiceData.CONTENTSERVICE,jsonObject,listener,errorListener);
            queue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchCustomerByContact(String mobileNo, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        JSONObject json = new JSONObject();
        try {
            json.put("request","search_customer_by_contact");
            json.put("contact_no",mobileNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.DATAURL,json,listener,errorListener);
        queue.add(request);
    }

    @Override
    public void searchCustomerByVehicle(String vehicleNo, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        JSONObject json = new JSONObject();
        try {
            json.put("request","search_customer_by_vehicle");
            json.put("vehicle_no",vehicleNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.DATAURL,json,listener,errorListener);
        queue.add(request);
    }

    @Override
    public void bookJob(BookingDetails bookingDetails, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Gson gson = new Gson();
        String json = gson.toJson(bookingDetails);
        try {
            JSONObject reqJSON = new JSONObject(json);
            reqJSON.put("request","book_job");
            reqJSON.put("booked_by", AppPreferences.getInstance(context).getUserId());
            System.out.println(reqJSON);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, ServiceData.BASEURL+ServiceData.JOBSERVICE,reqJSON,listener,errorListener){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String res = new String(response.data,"UTF-8");
                        System.out.println(res);


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    return super.parseNetworkResponse(response);
                }
            };

            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getJobList(String userId, String status, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

    }

    @Override
    public void updateCustomer(CustomerInformation customerInformation, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(customerInformation);
        try {
            JSONObject reqJson = new JSONObject(jsonString);
            reqJson.put("request","update_customer");
            reqJson.put("vehicle_no",BookingDetails.getInstance().getVehicleRegNo());
            System.out.println("Request : "+reqJson);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,ServiceData.BASEURL+ServiceData.DATAURL,reqJson,listener,errorListener){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String res = new String(response.data,"UTF-8");
                        System.out.println(res);


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    return super.parseNetworkResponse(response);
                }
            };
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateVehicle(VehicleInformation vehicleInformation, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(vehicleInformation);
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
            json.put("request","update_vehicle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,ServiceData.BASEURL+ServiceData.DATAURL,json,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    @Override
    public void getAllJobs(String userId, Response.Listener listener, Response.ErrorListener errorListener) {

    }

    public void uploadPicture(final String image, Response.Listener<String> listener, Response.ErrorListener errorListener){

        StringRequest request = new StringRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.JOBSERVICE,listener,errorListener){

            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("request","upload_picture");
                params.put("image",image);
                params.put("booking_id",BookingDetails.getInstance().getBookingId());
                return params;
            }
        };
        queue.add(request);
    }


}
