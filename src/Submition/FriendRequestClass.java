
package Backend;

public class FriendRequestClass {
    private final String requestId;
    private final String senderId;
    private final String receiverId;
    private final String status;

    public FriendRequestClass(String requestId, String senderId, String receiverId,String status) {
        this.requestId = requestId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    @Override
    public String toString() {
        return "Request from: " + senderId;
    }
}
//public class FriendRequestClass {
//   
//    private static int reqID=1; 
//    private String senderId;
//    private String receiverId;
//    private String status; 
//    private LocalDate requestDate;
//
//    public FriendRequestClass( String senderId, String receiverId) {
//        this.reqID=reqID++;
//        this.senderId = senderId;
//        this.receiverId = receiverId;
//        this.status = "pending";
//        this.requestDate = LocalDate.now();
//    }
//// Getters and setters
//   
//
//    public void setSenderId(String senderId) {
//        this.senderId = senderId;
//    }
//
//    public void setReceiverId(String receiverId) {
//        this.receiverId = receiverId;
//    }
//
//  
//    public String getRequestId() {
//        String str=String.valueOf(reqID);
//        return str;
//    }
//
//    public String getSenderId() {
//        return senderId;
//    }
//
//    public String getReceiverId() {
//        return receiverId;
//    }
//
//   
//
//    public LocalDate getRequestDate() {
//        return requestDate;
//    }
//    
//}
//