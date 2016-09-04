package com.mycompany.smartalbum.back.utils;

/**
 * Enumération des différentes vues de l'application
 * 
 * @author <b>amvou</b>
 */
public enum ViewEnum {
    
    /**
     * Vue index de la page d'accueil
     */
    HOME_VIEW("/page/home/index"),
    
    SEARCH_VIEW("/page/search/index"),
    
    SEARCH_ALBUM_VIEW("/page/search/result/albumResult"),
    
    SEARCH_IMAGE_VIEW("/page/search/result/imageResult"),
    
    SEARCH_SHELF_VIEW("/page/search/result/shelfResult"),
    
    SEARCH_TAG_VIEW("/page/search/result/tagResult"),
    
    SEARCH_USER_VIEW("/page/search/result/userResult"),
    
    PICTO_LIST_VIEW("/page/upload/list"),
    
    FILE_UPLOAD_VIEW("/page/upload/fileUploadForm"),
    
    FILE_UPLOAD_VIEW_SUCCESS("/page/upload/success"),
    
    CURRENT_PAGE_VIEW("/page/upload/galery"),
    
    ALBUM_CREATION_VIEW("/page/album/body/createAlbum"),
    
    ALBUM_DETAIL_VIEW("/page/albums/album"),
    
    IMAGES_MANAGER_VIEW("/page/images/imagesForm"),
    
    IMAGE_SUMMARY_VIEW("/page/albums/body/blog"),
    
    IMAGE_DETAIL_VIEW("/page/images/body/imageBlog"),
    
    SHELF_DETAIL_VIEW("/page/shelves/shelf"),
    
    PUBLIC_SHELVES_VIEW("/page/public/publicShelves"),
    
    PUBLIC_CHAT_ROOM("/page/chat/index"),
    
    USER_SHELVES_VIEW("/page/shelves/userShelves"),
    
    COMMON_CONTACT_VIEW("/common/contact"),
    
    COMMON_UNDERCONSTRUCTION_VIEW("/common/under-construction"),
    
    COMMON_FULL_SCREEN_PAGE_VIEW("/page/images/fullScreenSlider"),
    
    COMMON_ABOUT_VIEW("/common/about"),
    
    SMARTALBUM_REDIRECT_TO_PUBLICSHELVES("redirect:/shelvesController/publicShelves.html"),
    
    SMARTALBUM_REDIRECT_TO_LOGOUT("redirect:/home/logout.html"),
    
    SMARTALBUM_SUCCEED_VIEW("/common/operation_succeed"),
    
    SMARTALBUM_FAILED_VIEW("/common/operation_failed"),
    
    SMARTALBUM_REDIRECT_TO_MYSHELVES("redirect:/shelvesController/myShelves.html"),
    
    SMARTALBUM_REDIRECT_TO_DEFAULT_PICTURE_VIEW("/imagesController/paintDefaultImage/default/noimage_small120.jpg.html"),
    
    SMARTALBUM_VIEW_ERROR("/common/error"),
    
    SMARTALBUM_VIEW_CREATE_USER("/page/users/register"),
    
    SMARTALBUM_VIEW_EDIT_NEW_PASSWORD("/password/editNewPassword"),
    
    SMARTALBUM_VIEW_CONFIRM_MAIL_PASSWORD("/page/password/emailCheckForPassword"),
    
    SMARTALBUM_VIEW_RESET_PASSWORD("/page/password/reset/resetPassword"),
    
    SMARTALBUM_MESSAGEHTML_TEMPLATE("/page/tiny/messages"),
    
    SMARTALBUM_COMMENT_TEMPLATE("/page/images/body/comments"),
    
    SMARTALBUM_COMMENT_ITEM_TEMPLATE("/page/images/body/comment-item"),
    
    SMARTALBUM_MAIL_ERROR_VIEW("/page/password/error/mailError"),
    
    SMARTALBUM_LOGINPAGE_TEMPLATE("/page/login/index");
    
    
    /**
     * La vue courante de l'énumération
     */
    private String view;
    
    private ViewEnum(String view) {
        this.view = view;
    }
    
    public String getView() {
        return view;
    }
}
