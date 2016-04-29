package w3.com.okhttpdemo.mediagallerydemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class YoCommon {

    // Common values
    public static final String EMPTY_STRING = "";
    public static final String SPACE_STRING = " ";
    public static final String BACK_SLASH = "/";
    public static final String NOT_FOUND = "Not Found";
    // public static ExchangeCreation gExchangeCreation;

    private static String baseUrl = "http://staging-engine.yo.com/";

//    private static String baseUrl = "http://staging-engine.yo.com:9000/";
    //private static String baseUrl = "http://52.2.121.207/";
    //	public static DBContactCaching gDBContactCaching;
    //	public static PBContactCachingTask gPBContactCachingTask;
    // for tornado server
    //private static String baseUrl = "http://52.2.13.103:9900/";
    //private static String baseUrl = "http://52.2.13.103:8000/";
    //private static String baseUrl = "http://192.168.2.105:8800/";  //Arif
    //private static String baseUrl = "http://192.168.2.32:8800/";   //sabrina

    //private static String baseUrl = "http://servemev2.yo.com/";


    //RabbitMQ connection reset url - live
    //public static final String RABBITMQ_CONNECTION_RESET_URL = "http://eliminator.yo.com/remove_connection";

    //RabbitMQ connection reset url - staging
    public static final String RABBITMQ_CONNECTION_RESET_URL = "http://52.2.56.250:1100/remove_connection";

    //RabbitMQ connection reset url - local
//    public static final String RABBITMQ_CONNECTION_RESET_URL = "http://192.168.2.105:1100/remove_connection";

    private static boolean isBaseURLNotUpdated = true;
    public static int NOTIFICATION_ID = 0; //
    public static String api = "b0Kas0KaManusH";

    // Database related common key
    public final static String RESET_VALUE = "0";
    public final static String SET_VALUE = "1";

    // public final static String INVITATION_URL = "http://get.yo.com";

    // microsoft translation keys
    public static final String clientTranslationID = "_jhgfuywrt--jhfgr";
    public static final String clientTranslationSecretKey = "DTmlfegCk108cENU6f4khvjeLKqmaFL5JvXQqSAWT2I=";

    public static String files_locations = "/YO!/";
    // +Mimo 09-02-2015 Issue 393
    public static final String files_apk_locations = "/YO!/YO! Backup APK";
    public static final String video_locations = "/YO!/YO! Videos";
    public static final String audio_locations = "/YO!/YO! Received Audios";
    public static final String image_locations = "/YO!/YO! Received Images";
    public static final String image_compressed_locations = "/YO!/.YO! Compressed Image/";
    public static final String apk_locations = "/YO!/YO! Documents";
    public static final String rec_locations = "/YO!/YO! Recorded Audios";
    // -Mimo 09-02-2015 Issue 393
    public static final String icon_location = "/YO!/.icons/";
    public static final String cache_location = "/YO!/.cache/";
    public static final String files_locations_photo = "/YO!/YO! Captured Images/";
    public static final String error_log_location = "/YO!/.Error_Log/";
    public static final String connectivity_log_location = "/YO!/Connectivity_Log/";

    public static String error_log_file_name = "error_log.txt";

    public static String cam_directory = "/DCIM/Camera/";
    public static String REG_STR = "[^\\d+]";
    public static String ALPHANUMERIC_STR = "[^A-Za-z0-9]";
    public static String countryCodeApi = "http://ipinfo.io/country";
    private static String filePath = null;
    public static String fileType = null;
    public static int maxImageCount = 10;
    public static final String MAXIMUM_TIME = "2114-12-12 06:51:14.247";// added
    // by
    // Anjan

    // seen-delivery
    public static final String PENDING = "pending";
    public static final String SENDING = "sending";
    public static final String TRANSFERRING = "transferring...";
    public static final String SEND = "sent";
    public static final String DELIVERED = "delivered";
    public static final String UNSEEN = "unseen";
    public static final String SEEN = "seen";
    public static final String FAILED = "not delivered";
    public static final String ADD_SENT = "adsent";
    //public static  boolean OPEN_CLOSE_HOTSPOT_POPUP = false;

    // ack values
    public static final String TYPING = "#tp";// typing
    public static final String TYPING_FINISH = "#tf";// typing finish
    public static final String ACK_MESSAGE_RECEIEVE = "#acm";// acknowledge
    // message
    public static final String ACK_MESSAGE_VIEWED = "#acv";// acknowledge viewed

    public static final String FACEBOOK_FRIEND_ADDED = "facebook_friend_add";
    public static final String CONTACT_FRIEND_ADDED = "contact_friend_add";
    public static final String FRIEND_AVATAR_CHANGED = "update_avatar_friend";
    public static final String FRIEND_USERNAME_CHANGED = "update_username";
    public static final String ADD_CONTACT_REQUEST = "add_contact_request";

    public static final String ONLINESTATUSCHECKING = "#rab_online@check";// online
    // status
    // checking
    public static final String ACK_ONLINESTATUSCHECKING = "ack#rab_online@check";

    public static final String TYPINGINDICATOR = "tp";
    public static final String ACK_MESSAGE_RECEIEVE_INDICATOR = "ack";
    public static final String ONLINE_STATUS_CHECKING_INDICATOR = "online_check";

    public static boolean mIsInForegroundMode = false;

    // Block unblock variables
    public static final String NOT_IN_LIST = "notInList";
    public static final String OLD_BLOCKED_USER = "OldButBlocked";
    public static final String OLD_UNBLOCKED_USER = "OldButUnBlocked";
    public static int FROMSTARTCHATTING = 0;

    public static String FriendsTitleName = "";

    public static String ALL_YO_USER = "all_yo_user";
    public static String ONLY_CONTACT = "only_contact";
    public static String NOBODY = "no_body";


    // checking message type
    public static final String out_msg = "outgoing", inc_msg = "incoming",
            failed_msg = "outgoing", new_msg_marker = "newsms", contact_req_sent = "contactReq",
            date_marker = "date_marker", loadMoreMessage = "load_more";// 20.9.2014
    // pending by
    // Asiq & Anjan

    // type checking
    public static String message_indicator = "this_is_yo_msg",
            img_indicator = "this_is_yo_img",
            video_indicator = "this_is_yo_video", audio_indicator = "ya",
            file_type_indicator = "_file_", music_indicator = "ym";
    public static final String AUDIO_MUSIC_EXTENSION = "mp3",
            YO_RECORD_IDENTIFIER = "YO_Record_";

    public static final String localCommunication = "0",
            internetCommunication = "1";

    // file status states
    public static final String FILE_STATE_NOT_DOWNLOADED = "_f_not_downloaded",
            FILE_STATE_DOWNLOADED = "_f_downloaded",
            FILE_STATE_DOWNLOADING = "_f_downloading",
            FILE_STATE_INTERRUPTED = "_f_interrupted",
            FILE_STATE_NOT_UPLOADED = "_f_not_uploaded",
            FILE_STATE_UPLOADED = "_f_uploaded",
            FILE_STATE_UPLOADING = "_f_uploading";

//    public static String getBaseUrl(Context context) {
//        if (isBaseURLNotUpdated) {
//            if (context != null && OnPrefManager.init(context.getApplicationContext()).getIsInChina(false))
//                baseUrl = "http://servemev2.yochuan.cn/";
//            isBaseURLNotUpdated = false;
//        }
//        return baseUrl;
//    }

    public static String getNickName(String queName) {
        String[] splitstr = queName.split(YoCommon.SPLITTER_STRING);
        return splitstr[0];
    }

    public static void setShareFilePath(String path, String type) {
        filePath = path;
        fileType = type;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static String getFileType() {
        return fileType;
    }

    // public static ArrayList<HashMap<String, String>>
    // itemDetailArrayListSelectedPerson = new
    // ArrayList<HashMap<String,String>>();
    // public static int personPosition;
    // public static int personPositionReal;

    //
    public static String zero = "0";
    public static String one = "1";

    public static boolean isIconFileNotExist = false;

    // to keep notification number in mind
    public static List<String> numberofPerson = new ArrayList<>();

    public static String internet = "internet";

    public static final String FAVBROADCAST = "fav_broadcast";

    public static final String FAVBROADCAST_LOCAL = "fav_broadcast_local";

    // common notification variables
    // public static StringBuilder name_appender = new StringBuilder();
    public static ArrayList<String> notification_dispname_appender_list = new ArrayList<>();
    public static ArrayList<String> notification_unique_key_appender_list = new ArrayList<>();
    public static int msg_appender;

    // messaging page marker
    // public static final String DIRECT_GALARY_IMAGE_OPEN = "_dgio",
    // DIRECT_CAMERA_IMAGE_OPEN = "_dcio", DIRECT_GALARY_VIDEO_OPEN = "_dgvo",
    // DIRECT_CAMERA_VIDEO_OPEN = "_dcvo", DIRECT_CALL_OPEN = "_dco",
    // DIRECT_FILE_OPEN = "_dfo", DIRECT_RECORDER_OPEN = "_dro";

    // internal file sharing marker

    public static final String DIRECT_SHARE_MULTIPLE_IMAGE = "_dsmi";
    public static final String EXTERNAL_SHARE_MULTIPLE_FILES = "ex_share_multiple_files";
    // to keep internal sharing objects
    private static ArrayList<String> itemSelectedObjects = new ArrayList<>();
    private static String itemSelectedObjectsImage = "";
    private static String shareObjectType = new String();

    public static void setShareObjects(ArrayList<String> urlList) {
        itemSelectedObjects = urlList;
    }

    public static void clearSharedObjects() {
        itemSelectedObjects.clear();
    }

    public static void setShareObjects(ArrayList<String> list, String type) {
        itemSelectedObjects.addAll(list);
        shareObjectType = type;
    }

    public static void setShareObjectsImage(String list, String type) {
        itemSelectedObjectsImage = list;
        shareObjectType = type;
    }

    public static ArrayList<String> getShareObjects() {
        return itemSelectedObjects;
    }

    public static String getShareObjectType() {
        return shareObjectType;
    }

    public static void clearObjects() {
        if (!itemSelectedObjects.isEmpty()) {
            itemSelectedObjects.clear();
            shareObjectType = new String();
        }
    }

    // Message Seen Status
    // public static ArrayList<String> CurrentResendCoId = new
    // ArrayList<String>();

    public static ArrayList<String> StoreCoId = new ArrayList<>();
//    public static ArrayList<ChatAdapterDataModel> StorSeenStatus = new ArrayList<>();


    public static ArrayList<String> StoreCoIdForPushNoti = new ArrayList<>();
//    public static ArrayList<ChatAdapterDataModel> StoreDatamodelForPushNoti = new ArrayList<>();

    // public static ChatAdapterDataModel StorSeenStatus2;

    // video progress status
    public static ArrayList<String> fileIdentity = new ArrayList<>();
//    public static ArrayList<ChatAdapterDataModel> storedDataset = new ArrayList<>();

    // block popup
    public static final String NEW_USER_POPUP = "_nup";

    public static String phoneReadStatus;
    public static final String SEND_SYNC_END = "sync_done";

    // public static final String CONNECTIVITY_STATE_CHECKER_HOSTNAME =
    // "https://www.google.com";//"http://serveme.yo.com/";//"http://graph.facebook.com/zuck";//"https://www.google.com";

    // hyperlocal user data
    public static List<String> hyperLocalQueueList = Collections.synchronizedList(new ArrayList<String>());
//    public static List<FriendsAdapterDataModel> hyperLocalUserDataList = Collections.synchronizedList(new ArrayList<FriendsAdapterDataModel>());
    public static List<String> hyperLocalQueueListCache = Collections.synchronizedList(new ArrayList<String>());

    //public static ArrayList<String> hyperLocalBackupQueueList = new ArrayList<>();

    public static final String THIS_USER_PRESENT_IN_MESSAGING_PAGE = "current_user";
    public static final String THIS_USER_NOT_PRESENT_IN_MESSAGING_PAGE = "other_user";

    public static String HOTSPOT_NAME = "YO-Hotspot";

    // for block pop-up
    /*
     * public static boolean unknownHyperLocalUser = false;
	 *
	 * public static String backup_senders_name = null; public static String
	 * backup_senders_ip = null; public static String backup_senders_imgString =
	 * null; public static String backup_type = null; public static String
	 * backup_msg = null; public static String backup_pho_num = null; public
	 * static String backup_co_id = null; public static String backup_disp_name
	 * = null;
	 */

    // no_friends_layout_for_pb_fb phone number for local registered user
    public static String demo_phone_number = "00000000000";

    public static String Splitme(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /*
     * public static void setPhoneNumbers(String num){
	 *
	 * phone_num = Splitme(num);
	 *
	 * }
	 */

	/*
     * public static String getPhoneNumber() { return phone_num; }
	 */

    public static final String SPLITTER_STRING = "_@_";// "_#_";
    public static boolean external_share = false;

    public static boolean isHyperLocalScanProcessAllowed = true;

    public static final int notificationIdForskippedUser = 7784;
    public static final String TRIGGER_REG_VIEW = "_tr_reg_view";

    public static final ArrayList<String> myList = new ArrayList<>();
    public static int i = 0;


    // Broadcast action variable
    public static String REFRESH_HYPER_LOCAL_USER_DATA = "refresh_local_friend_list";

    public static final String TIMELINE_MESSAGE = "TimeLine_Message";
    //public static final String REQUEST_MESSAGE = "Request_Message";
    public static final String SEENDELIVERYSTATUS = "Seen_Message";
    public static String NEW_LOCAL_MESSAGE = "n_l_m";
    public static String DATA_LOADED = "data_loaded";
    public static final int MAX_AUDIO_RECORDING_SECOND = 60;

    /*
     * ++Tarun 15-12-2014 (To refresh yofriends page after making favorite a
     * person from hyperlocal)
     */
    public static boolean haveToRefresh = false;
    public static final int DEFAULT_USER_SELECTED_TAB = 0;

    /*
     * ++Tarun 18-12-2014 (to solve the problem after viewing message from popup
     * and then press the back button view flag)
     */
    public static boolean popUpViewClicked = false;
    public static boolean showMediaOverMobileDataWarning = true;

    public static final String NO_THUMB_INDICATOR = "no_thumb",
            DEMO_VIDEO_DURATION = "00:00", OTHERS_FILE_INFO = "no_info",
            DEMO_FILE_SOURCE_FOR_HYPER_LOCAL = "no_source";
    /*public static final int thumbWidth = 180;
    public static final int thumbHeight = 180;*/

    public static final String unKnown = "unknown";
    public static final String Known = "known";

    public static String SKIPUSERQUEUEUPDATE = RESET_VALUE;
    public static final String RECEIVED_MESSAGE_AFTER_CONTACT_CHANGED = "contact_changed";

    public static int NUMBER_OF_PENDING_UPLOAD_DOWNLOAD = 0,
            FILE_SHARING_NOTIFICATION_ID = 8874;

    public static String CONNECTIVITY_STATE_CHANGED = "_csc";

    // MEHDI: 24 MAR, 2015
    public static String EMERGENCY_LEVEL_BLOCKER = "Blocker";
    public static String EMERGENCY_LEVEL_NOTIFICATION = "Notification";

    public static String SERVER_NAME_TWILLO = "0";
    public static String SERVER_NAME_INFOBIP = "1";

    public static String UPDATE_INSTALL = "update_install";

    //prodip 23/3/2015
    //public static MainActivity mMainInstance;

    public static boolean isLocalUserScanningFirstTime = true;

    // MIMO 31/3/15
    public static int getLastMessage = 0;


    public static String[] session_id = {
            "19147741506090567490",
            "69144035501260281757",
            "57473218371823134089",
            "59161649719683464339",
            "37018139135342288793",
            "32880959732003873092",
            "15076072170448346345",
            "77249894696159898544",
            "51340209842031830836",
            "81231863600149440996"
    };

    public static String DEVICE = "android";
    public static String USER_AGENT = "YO";
    public static String AUTHORIZATION = "YO.LOTD.ANDROID.IPHONE.V1";
    public static String IDENTITY = "https://www.yo.com/";


    /*
         * +Tarun 24-03-2015 To Store Friends info to switch from local to internet
         */
    //public static ArrayList<String> StorCoIdToFindoutFailedMessage = new ArrayList<>();
//    public static ArrayList<ChatAdapterDataModel> StorChatAdapterDataModelToFindoutFailedMessage = new ArrayList<>();
    //public static final String SHOW_YOUR_POPUP = "showing_message_popup";
    public static final String LOCAL_FAILED_MESSAGE_BROADCAST_KEY = "_fmbk";
    public static final String FRIEND_ADDED_OR_BLOCKED_KEY = "_f_blocked";

    public static final String DELETE_IMAGE_FROM_DATABASE = "delete_image_from_database";
    /*
     * +Tarun 24-03-2015 To Store Friends info to switch from local to internet
     */
    public static final String DO_YOUR_TASK = "doingTheFinalTask";

    public static final String DEMO_STRING_VALUE = "no_data";

    //public static boolean isFailedFile = false;

    //public static boolean isVideoPageOn = false;

   /* public static final String InternetMessagingConnectorLog = "messagingServerConnectorLog.txt";
    public static final String internetMessengeReceiverLog = "internetMessengeReceiverLog.txt";
    public static final String internetMessengeSenderLog = "internetMessengeSenderLog.txt";
    public static final String errorSenderLog = "errorSenderLog.txt";*/

    public static final String SERVER_SYNC_UPDATE = "_ssU";


    public static final String hyperLocalRequestKey = "e13df5d72aa419414ef156c6b54c72f218c581b9";//sha1 encode of "are_you_yo_user";
    public static final String hyperLocalResponseKey = "ae018af23861656b2deca92b9365dc0be978e79d";//sha1 encode of "yes_i_am_yo_user";
    public static String hyperlocal_scannigKey = "are_you_yo_user?yes_i_im";

    public static final int HYPERLOCAL_USER_SCAN_VIEW_REFRESH_DELAY = 6 * 1000;
    public static final int HYPERLOCAL_USER_SCAN_CORE_OPERATION_REFRESH_DELAY = 60 * 1000;
    public static final int HYPERLOCAL_USER_SCAN_CONCURRENT_CORE_OPERATION_REFRESH_DELAY = 1 * 1000;
    public static final int HYPERLOCAL_USER_SCAN_CONCURRENT_UDP_OPERATION = 4;

    //++Aziz 15.06.15
    /*public static final String NEW_YO_USER_REQUEST = "new_user_request";
    public static final String NEW_YO_USER_RESPONSE = "get_new_user_request";*/
    public static final String YO_LAUNCHED = "yo_launch";
    public static final String USER_NAME_CHOSEN = "user_name_chosen";
    public static final String USER_PHOTO_ADDED = "photo_added";
    public static final String YO_INITIAL_INSTALL_INDICATOR = "yo_installing";
    public static final String INITIAL_INSTALLATION_MESSAGE = "installation_message";
    public static final String USER_CONNECTED_ON_HOTSPOT = "user_connected";
    public static String CONNECTED_DEVICE_IP_ADDRESS = null;
    public static final String YO_INSTALLATION_FINISHED = "installation_finish";
    public static final String YOCONVERSATION_PAGE_OPEN_INDICATOR = "open_indicator";
    public static final String YOCONVERSATION_PAGE_OPEN_INDICATOR_FOR_YO_SENDER = "open_indicator_for_yo_sender";
    //--Aziz 15.06.15

    /*-prodip
     * Keeping unchanged the selected image in portrait and landscape mode
     */
    public static final String SAVE_ADAPTER_STATE = "save_adapter";
    /*-prodip*/

    public static boolean isLocalUserNotifyOperationAllowed = true;
    public static final int NOTIFICATION_ID_NEW_HYPER_LOCAL_USER = 7785;
    public static final int NOTIFICATION_ID_CONTACT_ID = 7786;

    public static final String CONTACT_ADD_MESSAGE = "ADD ME",
            CONTACT_ADD_REPLY = "ADD DONE",
            CONTACT_ADD_CANCEL = "ADD CLOSE",
            CONTACT_ADD_DECLINE = "ADD CANCEL",
            CONTACT_ADD_SUCCESS = "ADD Success",
            contactAdd_indicator = "contact_add_info";

    public static int notificationId = 1337;
    public static boolean isConVersationActivityFocused = false;

    public static List<String> pendingInternetMessageCorRelationId = new ArrayList<>();

   /* public static String YES_ACTION="YES_ACTION";
    public static String NO_ACTION="NO_ACTION";
    public static String DEFAULT_ACTION="null_action";*/

//    public static volatile SortedSet<Long> unconfirmedSet =
//            Collections.synchronizedSortedSet(new TreeSet());


    public static final String USER_PROFILE_IMAGE_RESPONSE = "profile_img_response";
    public static final String USER_PROFILE_IMAGE_REQUEST = "profile_img_request";
    public static final String USER_PROFILE_IMAGE_REQUEST_INDICATOR = "profile_image_indicator_request";
    public static final String USER_PROFILE_IMAGE_RESPONSE_INDICATOR = "profile_image_indicator_response";
    public static final String user_demo_image_indicator = "_udii", user_real_image_indicator = "_urii";
    public static final String USER_IMAGE_BROADCAST = "userImageBroadcastKey";

    public static boolean IS_PROFILE_PICTURE_CHANGED = false;
    public static boolean OPEN_CLOSE_HOTSPOT_POPUP = false;
    public static boolean OPEN_CLOSE_HOTSPOT_POPUP_FOR_INSTANT_TRANSFER = false;
    public static boolean HOTSPOT_OPEN_PREVIOUSLY = false;

    public static boolean isViewed = false;
    public static boolean isInTimeline = false;

    public static boolean isInFriendRequest = false;
    public static boolean isUnreadMessage = false;
    public static final String NAV_SELECTED_POSITION = "navPosition";

    public static int toolTipCounter = 0;

    // --9-7code Azim-Wasim
    //Order of this codes are important
    public static final int FRIEND_STATUS_DEFAULT = 0;
    public static final int FRIEND_STATUS_REQUEST_SENT = 1;
    public static final int FRIEND_STATUS_REQUEST_FAILED = 2;
    public static final int FRIEND_STATUS_REQUEST_DECLINED_FROM_FRIENDS = 3;
    public static final int FRIEND_STATUS_FRIEND_NOW = 4;
    public static final int FRIEND_STATUS_REQUESTED_BY_OTHER = 5;
    public static final int FRIEND_STATUS_REQUEST_DECLINED_BY_ME = 6;
    public static final int FRIEND_STATUS_NEW_MESSAGE = 7;  // who is unknown on hyper net list
    public static final int FRIEND_STATUS_BLOCKED = 8;
    public static final int FRIEND_STATUS_PENDING_REQUEST = 9;
    public static final int FRIEND_DELETED = 10;
    public static final int FRIEND_STATUS_SUGGESTED_FRIEND = 11;
    public static final int FRIEND_STATUS_REQUEST_ACCEPT_FAILED = 12;
    public static final int FRIEND_STATUS_BLOCKED_OLD_DELETED = 13;
    public static final int FRIEND_STATUS_OLD_DELETED_FRIEND_REQUESTED_BY_OTHER = 14;
    public static final int FRIEND_STATUS_FRIEND_NOW_REQUESTED_BY_OTHER = 15;

    public static final int FRIEND_STATUS_SUGGESTED_FRIEND_BUT_BLOCKED = 16;
    public static final int FRIEND_STATUS_KNOWN_FRIEND_BUT_BLOCKED = 17;
    public static final int FRIEND_STATUS_REQUEST_SENT_BUT_BLOCKED = 18;
    public static final int FRIEND_STATUS_UNKNOWN_FRIEND_BUT_BLOCKED = 1;


    //  public static int FRIEND_STATUS = -1;

    public static final int FRIEND_STATUS_NOT_SYNCED = 0;
    public static final int FRIEND_STATUS_SYNCED = 1;
    //This value should be dynamically created rather APP Start time value
    public static final String CONTACT_INITIAL_TIMESTAMP = String.valueOf(System.currentTimeMillis());
    //public static final int FRIEND_NOT_DELETED = 0;

    //--9-7-2015 Friend status
    public static final String TAKE_MESSAGE = "Take_Message";
    public static final String REQUEST_MESSAGE = "Request_Message";
    public static boolean stateChangeYoPhotos = false;
    public static boolean stateChangeYoAudios = false;
    public static boolean stateChangeYoVideos = false;

    public static final String SERVER_HIT_PURPOSE_REGISTRATION = "registration",
            SERVER_HIT_PURPOSE_SEND_SMS = "send_sms",
            SERVER_HIT_PURPOSE_CONTACT_MATCH = "contact_matching",
            SERVER_HIT_PURPOSE_FRIEND_LIST_BACKUP = "insert_friends",
            SERVER_HIT_PURPOSE_FACEBOOK_REGISTRATION = "registration_facebook",
            SERVER_HIT_PURPOSE_FACEBOOK_CONTACT_MATCH = "contact_matching_facebook",
            SERVER_HIT_PURPOSE_GCM = "sns_gcm",
            SERVER_HIT_PURPOSE_GCM_NEW = "sns_gcm_new",
            SERVER_HIT_PURPOSE_LINK_FACEBOOK = "link_facebook",
            SERVER_HIT_PURPOSE_LINK_ADDRESS_BOOK = "link_phone_number",
            SERVER_HIT_PURPOSE_TEST_LOGIN_PROCESS = "test-login-process",
            SERVER_HIT_PURPOSE_MAKE_CALL = "make_call",
            SERVER_HIT_PURPOSE_LINK_ADDRESS_BOOK_V2 = "link_phone_number_v2",
            SERVER_HIT_PURPOSE_USER_AVATER_CHANGED = "update_profile_image_v2",
            SERVER_HIT_PURPOSE_GET_REGISTRATION_ID_LIST = "get_registration_id_list",
            SERVER_HIT_PURPOSE_APPLE_PUSH = "apple_push",
            SERVER_HIT_PURPOSE_ADD_REQUEST_PUSH = "friend_request_push",
            SERVER_HIT_PURPOSE_CHANGE_PHONE_NUMBER = "change_phone_number",
            SERVER_HIT_PURPOSE_UPDATE_REG_ID = "update_friend_registration_id",
            SERVER_HIT_PURPOSE_UPDATE_MY_APP_INSTALLED_VERSION = "update_version",
            SERVER_HIT_PURPOSE_GET_FRIENDS_INFO = "get_friend_info";


    public static String GCM_PUSH = "gcm_push";
    public static String SERVER_VERSION = "4"; // server version to keep track ofserver

    public static final String DEMO_CONTACT_ID = "0";
    public static final String DEMO_RAW_CONTACT_ID = "0";


    /**
     * Author: Al-Mamun
     * Date: 06-08-15
     * Issue: 1457
     */
  /*  public static final int chatThumbWidth = 250;
    public static final int chatThumbHeight = 250;*/

    /**
     * GCM credentials
     */
    public static final String GCM_PROJECT_ID = "45527734962";
   /* public static final String BAIDU_API_KEY = "dP2CUd2Mj6fEbOjZdj2DNfxv";
    public static final String GCM_NEW_FRIENDS = "GCM_NEW_FRIENDS";*/

    //++Aziz Friends original profile image request response indicator
    public static final String USER_ORIGINAL_IMAGE_REQUEST = "uoir";
    public static final String USER_ORIGINAL_IMAGE_RESPONSE = "uoire";

    //Friends original profile image name extention
    public static final String LARGE_IMAGE = "_large";

    public static final String REGISTRATIONTYPE_FACEBOOK = "fb";
    public static final String REGISTRATIONTYPE_YO = "yo";
    public static final String REGISTRATIONTYPE_PHONE = "ph";
    public static final String ProfilePicture = "profilepic.jpg";

    //Common name for own profile picture
    public static final String PROFILE_PICTURE = "profilepic.jpg";
    //Local user broadcast receiver action string
    public static final String LOCAL_USER_BROADCATS_ACTION = "local_user";
    public static final String LOCAL_USER_BROADCAST = "hypernet_user";

    public static final int maximumTestModeEnableCounter = 11;
    public static boolean isMeVisibleInDiscoveryList = false;
    public static int numberOffacebookCalled = 0;

    public static int firstIndex = 0;
    public static int lastIndex = 0;
    public static int firstIndexMessage = 0;
    public static int lastIndexMessages = 0;
    public static int firstIndexMyContacts = 0;
    public static int lastIndexMyContacts = 0;

    public static int FRIEND_REQUEST_FIRST_REMINDER = 5;
    public static int FRIEND_REQUEST_SECOND_REMINDER = 14;
    public static int FRIEND_REQUEST_ALARM_REQUEST_CODE = 20;
    public static int REMINDER_ALARM_REQUEST_CODE = 30;
    public static int FRIEND_REQUEST_LAST_REMINDER = 15 * 2;
    public static int AFTER_CONTACT_ADD_FROM_PROFILE_VIEW = 0;
    public static String AUDIO_CORELATION_ID = "";


    //++Onboarding 1.7.0
    public static final int FRIEND_UNKNOWN = 0;
    public static final int FRIEND_KNOWN = 1;
    //--Onboarding 1.7.0

    //Content discovery request response string
    public static final String CONTENT_DISCOVER_LIST_REQUEST = "request_list";
    public static final String CONTENT_DISCOVER_LIST_RESPONSE = "response_list";

    public static final String CONTENT_DISCOVER_THUMB_REQUEST = "thumb_request";
    public static final String CONTENT_DISCOVER_THUMB_RESPONSE = "thumb_response";

    public static final String CONTENT_DISCOVER_LARGE_CONTENT_REQUEST = "large_content_request";
    //    public static final String CONTENT_DISCOVER_LARGE_CONTENT_RESPONSE = "large_content_response";
//
//
//    public static final String discover_location = "/YO!/Content Discover Files/";
//    public static final String CONTENT_DESCOVERY_IMAGE_BROADCAST = "CONTENT_DESCOVERY_IMAGE_BROADCAST";
//
//    public static final String CONTENT_DESCOVERY_USER_IMAGE_BROADCAST = "CONTENT_DESCOVERY_USER_IMAGE_BROADCAST";
//
//    public static ArrayList<String> publicContentQnameList = new ArrayList<>();
//    public static ArrayList<Content> friendsPublicContentList = new ArrayList<>();
//    public static ArrayList<Content> myPublicContentlist = new ArrayList<>();
    public static final String INITIAL_THUMB_REQUEST = "initial_thumb";

    public static final String content_thumbs_location = "/YO!/.content thumbs/";

    public static HashMap<String, Integer> publicContentListMap = new HashMap<>();
//    public static HashMap<String, List<Content>> publicContentContentsMap = new HashMap<>();
    public static final String ALL_CONTENTS_THUMB_REQUEST = "all_thumb_request";
    public static final String ALL_CONTENTS_THUMB_RESPOSNE = "all_thumb_response";
    public static final String CONTENT_THUMB_BROADCAST = "content_thumb_broadcast";

    public static final String INITIAL_THUMB_BROADCAST = "initial_thumb_broadcast";
//    public static final HashMap<String, List<Content>> publicContentContentsOldMap = new HashMap<>();

    public static final String HYPE_NOTIFICATION_INDICATOR = "hype_notification_indicator";
    public static final int HYPE_STATUS_DEFAULT = 0;
    public static final int HYPE_STATUS_PENDING = 1;
    public static final int HYPE_STATUS_HYPED = 2;
    public static final int HYPE_STATUS_NOT_ANIMATION = 3;
    public static final String HYPE_NOTIFICATION_RESPONSE_INDICATOR = "hype_notification_response_indicator";

    public static final String REMOVE_UNREACHABLE_USER = "remove_user";
    public static final int ITEM = 0;
    public static final int SECTION = 1;
    public static final String DISCOVER_SORTING_KEY = "B";

    public static final String NEW_MESSAGE_PUSH_FORMAT = "NEW_MESSAGE_PUSH_FORMAT";
    public static boolean CURRENT_MOBILE_DATA_ON_OFF_STATUS = false;

    public static final String NO_FULL_NAME = "noFullName";
    public static final String NOTAVAILABLE = "NA";

    public static final String LOCAL_USER_PRESENCE_PING_REQUEST = "ping_request";
    public static final String LOCAL_USER_PRESENCE_PING_RESPONSE = "ping_response";
    public static final int BORDER_RADIUS = 2;
    public static boolean MULTIPLE_NOTIFICATION = false;
    public static int FLAG_FOR_FBLINKEDFRIENDS_ACTIVITY = 1;
    public static int FLAG_FOR_PHONE_VERIFICATION_ACTIVITY = 1;

    public static final String FILE_TYPE_APP = "APP";
    public static final String COMMON_FILE_TYPE = "FILE";
    public static final String FILE_TYPE_ZIP = "ZIP";
    public static final String FILE_TYPE_PHOTO = "PHOTO";

    public static final String FILE_TYPE_MUSIC = "MUSIC";

    public static final String FILE_TYPE_VIDEO = "VIDEO";
    public static final String FILE_TYPE_UNKNOWN = "UNKNOWN";

    public static long MILLISECONDS_UNTIL_REMAIN_FOR_FINISHED_HOTSPOT = 0;
    public static int NOTIFICATION_ID_FOR_ALERTING_HOTSPOT = 4241;
    public static String HotspotTimeControlTimeId = "";
    public static String HotspotTimeControlTimeBeforeNotify = "HotspotTimeControlTimeBeforeNotify";
    public static String HotspotTimeControlTimeAfterNotify = "HotspotTimeControlTimeAfterNotify";

    public static final String CONTENT_ACCESSIBILITY = "no_content";

}
