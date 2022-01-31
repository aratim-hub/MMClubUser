package com.appmart.mmcuser.utils;

public class ServerAddress {

    //request to Admin
    public final static String DOMAIN_OR_IP = "https://manageapp.mmclub.in";
    public final static String USER_BASE_URL = DOMAIN_OR_IP +"/user/";
    public final static String SEND_OTP = DOMAIN_OR_IP+"/send_otp.php";

    public final static String LOGIN_USER = USER_BASE_URL + "LoginRequest_replica.php";
    public final static String REGISTER_USER = USER_BASE_URL + "register_user.php";
    public final static String GET_ALL_TRAINNING_VIDEO = USER_BASE_URL + "get_all_training_video.php";
    public final static String GET_ALL_SUCCESS_STORIES_VIDEO = USER_BASE_URL + "get_all_success_stories_video.php";
    public final static String GET_ALL_MARKETING_IMAGES = USER_BASE_URL + "get_all_marketing_images.php";
    public final static String GET_ALL_MARKETING_VIDEO = USER_BASE_URL + "get_all_marketing_videos.php";
    public final static String GET_ALL_MARKETING_CONTENT = USER_BASE_URL + "get_all_marketing_Content.php";
    public final static String GET_ALL_NEWS = USER_BASE_URL + "get_all_news.php";
    public final static String GET_SUPPORT_TICKET = USER_BASE_URL + "get_support_ticket.php";
    public final static String ADD_NEW_SUPPORT_TICKET = USER_BASE_URL + "add_new_support_ticket.php";
    public final static String GET_REPLY_OF_TICKETS = USER_BASE_URL + "get_ticket_reply_by_ticket_id.php";
    public final static String ADD_NEW_REPLY_TO_TICKET = USER_BASE_URL + "add_new_reply_to_ticket.php";
    public final static String GET_MY_DOWNLINK = USER_BASE_URL + "get_my_downlink.php";
    public final static String UPDATE_USER_STATUS = USER_BASE_URL + "update_user_status.php";
    public final static String GET_USER_STATUS_TABLE = USER_BASE_URL + "get_user_status_table.php";
    public final static String GET_WA_LINK = USER_BASE_URL + "get_wa_link.php";
    public final static String GET_ALL_ACHIEVEMENT = USER_BASE_URL + "get_all_achievement.php";
    public final static String GET_ALL_SLIDER_IMAGES = USER_BASE_URL + "get_all_slider_images.php";
    public final static String UPDATE_DEVICE_TOKEN = USER_BASE_URL + "update_device_token.php";


    public final static String GET_ALL_NEX_MONEY_MULTISUCCESS = USER_BASE_URL + "get_multisuccess_nexmoney.php";
    public final static String GET_ALL_QA = USER_BASE_URL + "get_all_qa.php";
    public final static String GET_TRAINING_VIDEO_BY_CATEGORY = USER_BASE_URL + "get_training_video_by_category_new.php";
    public final static String GET_TRAINING_VIDEO_BY_CATEGORY1 = USER_BASE_URL + "get_training_video_by_category_new1.php";
    public final static String GET_VIDEO_IMAGES = USER_BASE_URL + "get_video_images.php";
    public final static String VALIDATE_COUPON = USER_BASE_URL + "validate_coupon.php";
    public final static String GET_REFERAL_LINKS = USER_BASE_URL + "get_referal_links.php";
    public final static String GET_REFERAL_LINKS_BY_DOWNLINK_USER_ID = USER_BASE_URL + "get_referal_links_by_downlink_user_id.php";
    public final static String CHECK_IF_BUSINESS_STARTED_OR_NOT = USER_BASE_URL + "check_if_business_started.php";
    public final static String UPDATE_REFERAL_LINK = USER_BASE_URL + "update_referal_link.php";
    public final static String UPDATE_REFERAL_LINK_AGAIN = USER_BASE_URL + "update_referal_link_again.php";
    public final static String UPDATE_PAYMENT_APPROVAL = USER_BASE_URL + "update_payment_approval.php";
    public final static String UPDATE_AS_TRAINING_COMPLETED = USER_BASE_URL + "update_training_completed.php";
    public final static String GET_HOST_DETAILS = USER_BASE_URL + "get_host_details.php";
    public final static String REQUEST_HOST_LINK = USER_BASE_URL + "request_host_link.php";
    public final static String GET_NO_OF_JOINING = USER_BASE_URL + "check_number_of_joining.php";
    public final static String GET_MMC_REFERAL_LINKS = USER_BASE_URL + "get_mmc_referal_links.php";
    public final static String GET_START_BUSINESS_VIDEO_LINK = USER_BASE_URL + "get_start_business_video_link.php";
    public final static String GET_VERSION_CODE = USER_BASE_URL + "get_version_code.php";
    public final static String GENERATE_CHECKSUM_URL = DOMAIN_OR_IP+"/paytm/generateChecksum.php";
    public final static String UPDATE_TRANSACTION_DETAILS = USER_BASE_URL+"emailReceipt/update_transaction_details.php";
    public final static String WITHDRAWAL_REQUEST_URL = USER_BASE_URL+"withdraw_request.php";
    public final static String CHECK_IF_TRAINING_COMPLETED = USER_BASE_URL+"check_if_user_training_completed.php";
    public final static String CHECK_FOR_ADMIN_APPROAL = USER_BASE_URL+"check_for_admin_approal.php";
    public final static String GET_PAYMENT_GATEWAY_DETAILS = USER_BASE_URL+"get_payment_gateway_details.php";
    public final static String GET_WALLET_HISTORY = USER_BASE_URL+"get_wallet_history.php";
    public final static String GET_WITHDRAW_HISTORY = USER_BASE_URL+"get_withdraw_history.php";
    public final static String GET_REFERAL_LINK_PENDING_REASON = USER_BASE_URL+"get_link_pending_reason.php";
    public final static String GET_UPLINE_SPONSOR_LINK = USER_BASE_URL+"get_upline_sponsor_link.php";
    public final static String GET_EVENT_LIST = USER_BASE_URL+"get_event_list.php";
    public final static String GET_EVENT_ATTENDANCE = USER_BASE_URL+"add_online_event_attendance.php";
    public final static String GET_LINK_REQUEST_FOR_ME = USER_BASE_URL+"get_link_request_for_me.php";
    public final static String GET_LINK_REEQUST_DETAILS = USER_BASE_URL+"get_link_request_details.php";
    public final static String PLACE_CHANGE_SPONSOR_REQUEST = USER_BASE_URL+"place_change_sponsor_request.php";
    public final static String GET_PLACED_SPONSOR_CHANGE_REQUEST = USER_BASE_URL+"get_placed_change_sponsor_request.php";
    public final static String GET_RECEIVED_SPONSOR_CHANGE_REQUEST = USER_BASE_URL+"get_received_change_sponsor_request.php";
    public final static String REQUEST_ACTION_URL = USER_BASE_URL+"change_sponsor_request_action.php";
    public final static String GET_ALL_MCQ = USER_BASE_URL+"get_all_mcq.php";
    public final static String GENERATE_NEXMONEY_COUPONS = USER_BASE_URL+"generate_nexmoney_coupons.php";
    public final static String CHANGE_SPONSOR_DIRECTLY = USER_BASE_URL+"change_sponsor_directly.php";
    public final static String GET_BUSINESS_GUIDE_VIDEO = USER_BASE_URL+"get_business_guide_video.php";
    public final static String GET_BUSINESS_GUIDE_CONTENT = USER_BASE_URL+"get_business_guide_content.php";
    public final static String GET_SPONSOR_TRAINING_CONTENT = USER_BASE_URL+"get_sponsor_training_content.php";
    public final static String GET_SPONSOR_TRAINING_VIDEO = USER_BASE_URL+"get_sponsor_training_video.php";
    public final static String GET_GREATSPONSOR_TRAINING_CONTENT = USER_BASE_URL+"get-great-sponsor-training-content.php";
    public final static String GET_GREATSPONSOR_TRAINING_VIDEO = USER_BASE_URL+"get_great_sponsor_training_video.php";
    public final static String GET_GO_MULTIINCOME_JOINING = USER_BASE_URL+"get_go_multiincome_joining.php";
    public final static String CHECK_IF_PAYMENT_APPROVED_BY_SPONSOR = USER_BASE_URL+"check_if_payment_approved_by_sponsor.php";
    public final static String PLACE_GO_MULTI_INCOME_REQUEST = USER_BASE_URL+"place_go_multi_income_request.php";
    public final static String GET_TELEGRAM_GROUP_LINKS = USER_BASE_URL+"get_telegram_group_links.php";
    public final static String GET_SPONSOR_LEVEL = USER_BASE_URL+"get_sponsor_level.php";
    public final static String GET_SPONSOR_NAME = USER_BASE_URL+"get_sponsor_name.php";
    public final static String UPDATE_WHATSAPP_NUMBER = USER_BASE_URL+"update_whatsapp_number.php";
    public final static String GET_ONLINE_EVENT_LIST = USER_BASE_URL+"get_online_event_list.php";
    public final static String ADD_NEW_ONLINE_EVENT = USER_BASE_URL+"add_new_online_event.php";
    public final static String CANCEL_ONLINE_EVENT_BY_EVENT_ID = USER_BASE_URL+"cancel_online_event_by_event_id.php";
    public final static String UPDATE_VIDEO_SEEN_STATUS = USER_BASE_URL+"update_video_seen_status.php";
    public final static String GET_USERID_NAMES = USER_BASE_URL+"get_userid_names.php";
    public final static String GET_COMPANY_SEQUENCE = USER_BASE_URL+"get_company_list.php";

    public final static String ADD_NEW_BUSINESS_TURNOVER = USER_BASE_URL + "add_new_business_turnover.php";
    public final static String GET_MY_PARENT_TURNOVER = USER_BASE_URL + "get_my_parnet_turnover.php";
    public final static String GET_MY_OVERALL_TURNOVER = USER_BASE_URL + "get_my_overall_turnover.php";
    public final static String GET_MY_PAST_TURNOVER = USER_BASE_URL + "get_my_overall_turnover.php";
    public final static String GET_MY_APPROVAL_LIST = USER_BASE_URL + "get_new_business_turnover_under_user.php";
    public final static String UPDATE_APPROVE_AND_REJECT_STATUS = USER_BASE_URL + "update_user_turnover.php";
    public final static String GET_CHILD_TURNOVER_COUNT = USER_BASE_URL + "get_child_pending_turnover_count.php";
    public final static String GET_CHILD_TURNOVER_STATUS = USER_BASE_URL + "get_my_turnover_status.php";

    public final static String SUBMIT_TRAINING_VIDEO = USER_BASE_URL + "submit_traning_video.php";
    public final static String SUBMIT_SPONSER_TRAINING_VIDEO = USER_BASE_URL + "submit_straning_video.php";
    public final static String SUBMIT_GREAT_SPONSER_TRAINING_VIDEO = USER_BASE_URL + "submit_gstraning_video.php";


    public final static String SUBMIT_TRAINING_VIDEO_STATUS = USER_BASE_URL + "submit_traning_video_status.php";
    public final static String SUBMIT_SPONSER_TRAINING_VIDEO_STATUS = USER_BASE_URL + "submit_straning_video_status.php";
    public final static String SUBMIT_GREAT_SPONSER_TRAINING_VIDEO_STATUS = USER_BASE_URL + "submit_gstraning_video_status.php";
    public final static String GET_COMPANY_SHOPEES = USER_BASE_URL + "get_company_shopees.php";
    public final static String ADD_EVENT_RATING = USER_BASE_URL + "add_online_event_ratings.php";
    public final static String EVENT_TITLE_LIST = USER_BASE_URL + "gurukul/get_even_subject_list.php";

}