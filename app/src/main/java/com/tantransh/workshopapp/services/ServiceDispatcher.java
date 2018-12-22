package com.tantransh.workshopapp.services;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tantransh.workshopapp.appdata.AppPreferences;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.jobbooking.data.CustomerInformation;
import com.tantransh.workshopapp.jobbooking.data.VehicleInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

public class ServiceDispatcher implements RequestListener{
    private RequestQueue queue;
    private ImageLoader imageLoader;
    private static  ServiceDispatcher instance;
    private static Context context;

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

    @Override
    public void login(final String userId, final String password, Response.Listener listener, Response.ErrorListener errorListener){
        JSONObject json = new JSONObject();
        try {
            json.put("userId",userId);
            json.put("password",password);
            json.put("request","login");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.AUTHURL,json,listener,errorListener){



            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println("Response : "+res);
                    JSONObject resJSON = new JSONObject(res);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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



    @Override
    public void searchVehicle(String vehicleId, Response.Listener listener, Response.ErrorListener errorListener) {
        vehicleId = vehicleId.replace(" ","%20");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,ServiceData.BASEURL+ServiceData.GETURL+"?vehicleNo="+vehicleId+"&request=vehicledetails",null,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    @Override
    public void addCustomer(CustomerInformation customerInformation, Response.Listener listener, Response.ErrorListener errorListener) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(customerInformation);
        JSONObject json = null;

        try {
            json = new JSONObject(jsonString);

            json.put("request","add_customer");
            json.put("vehicleNo",BookingDetails.getInstance().getVehicleRegNo());

            System.out.println("JSON: "+json);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.POSTURL,json,listener,errorListener){
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
    public void registerVehicle(VehicleInformation vehicleInformation, Response.Listener listener, Response.ErrorListener errorListener) throws JSONException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(vehicleInformation);
        JSONObject requestJSON = new JSONObject(jsonString);
        requestJSON.put("request","reg_vehicle");
        System.out.println(requestJSON);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.POSTURL,requestJSON,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);
                    JSONObject resJSON = new JSONObject(res);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }



    @Override
    public void getState(Response.Listener listener, Response.ErrorListener errorListener){

        JSONObject json = new JSONObject();
        try {
            json.put("request","state_list");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,ServiceData.BASEURL+ServiceData.GETURL+"?request=state_list",null,listener,errorListener);

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(request);
    }

    @Override
    public void getCities(String stateCode, Response.Listener listener, Response.ErrorListener errorListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ServiceData.BASEURL+ServiceData.GETURL,null,listener,errorListener){

        };

    }

    @Override
    public void getSpares(Response.Listener listener, Response.ErrorListener errorListener){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServiceData.BASEURL+ServiceData.GETURL+"?request=spare_list",null,listener,errorListener){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
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
    public void getServices(Response.Listener listener, Response.ErrorListener errorListener){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServiceData.BASEURL+ServiceData.GETURL+"?request=service_list",null,listener,errorListener){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
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
    public void getMake(Response.Listener listener, Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,ServiceData.BASEURL+ServiceData.GETURL+"?request=makelist",null,listener,errorListener){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    @Override
    public void getCurrentJobs(Response.Listener listener, Response.ErrorListener errorListener) {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,ServiceData.BASEURL+ServiceData.GETURL+"?request=current_jobs&bookingDate="+Validator.getCurrentDate()+"&userId="+AppPreferences.getInstance(context).getUserId(),null,listener,errorListener){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    @Override
    public void getOpenJobs(Response.Listener listener, Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,ServiceData.BASEURL+ServiceData.GETURL+"?request=open_jobs&userId="+AppPreferences.getInstance(context).getUserId(),null,listener,errorListener){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    @Override
    public void getJobCard() {

    }

    @Override
    public void getJobInformation(String jobId) {

    }

    @Override
    public void getItemList(Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("request","item_list");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ServiceData.BASEURL_2+ServiceData.APPCONTROLER,jsonObject,listener,errorListener);
            queue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchCustomerByContact(String mobileNo, Response.Listener listener, Response.ErrorListener errorListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,ServiceData.BASEURL+ServiceData.GETURL+"?request=search_customer_by_contact&contact="+mobileNo,null,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    @Override
    public void searchCustomerByVehicle(String vehicleNo, Response.Listener listener, Response.ErrorListener errorListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,ServiceData.BASEURL+ServiceData.GETURL+"?request=search_customer_by_vehicle&vehicleNo="+vehicleNo,null,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    @Override
    public void bookJob(BookingDetails bookingDetails, Response.Listener listener, Response.ErrorListener errorListener) {
        Gson gson = new Gson();
        String json = gson.toJson(bookingDetails);
        try {
            JSONObject reqJSON = new JSONObject(json);
            reqJSON.put("request","book_job");
            reqJSON.put("bookedBy", AppPreferences.getInstance(context).getUserId());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, ServiceData.BASEURL+ServiceData.POSTURL,reqJSON,listener,errorListener){
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
    public void getJobList(String userId, String status, Response.Listener listener, Response.ErrorListener errorListener) {

    }

    @Override
    public void changeCustomer(String customerId, String vehicleId, Response.Listener listener, Response.ErrorListener errorListener) {

    }

    @Override
    public void updateCustomer(CustomerInformation customerInformation, Response.Listener listener, Response.ErrorListener errorListener) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(customerInformation);
        try {
            JSONObject reqJson = new JSONObject(jsonString);
            reqJson.put("request","update_customer");
            reqJson.put("vehicleNo",BookingDetails.getInstance().getVehicleRegNo());
            System.out.println("Request : "+reqJson);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,ServiceData.BASEURL+ServiceData.PUTURL,reqJson,listener,errorListener){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String res = new String(response.data,"UTF-8");
                        System.out.println(res);


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
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
    public void updateVehicle(VehicleInformation vehicleInformation, Response.Listener listener, Response.ErrorListener errorListener) {
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,ServiceData.BASEURL+ServiceData.PUTURL,json,listener,errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
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

    public void uploadPicture(final String image, Response.Listener listener, Response.ErrorListener errorListener){
        JSONObject json = new JSONObject();
        try {
            json.put("request","upload_picture");
            json.put("image",image);
            json.put("bookingId",BookingDetails.getInstance().getBookingId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ServiceData.BASEURL+ServiceData.POSTURL,json,listener,errorListener ){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String res = new String(response.data,"UTF-8");
                    System.out.println(res);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }


}
