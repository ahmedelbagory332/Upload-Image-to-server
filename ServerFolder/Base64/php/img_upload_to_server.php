<?php 
include "DatabaseConfig.php";


if($_SERVER['REQUEST_METHOD'] == 'POST')
{
 
 $title = $_POST['title'];
 $image = $_POST['image'];
 
 $upload_path = "http://192.168.1.8/AndroidRetrofit/Base64/images/$title.png";
 $sql = "insert into images (image_path,title) values ('$upload_path','$title')";

 if(mysqli_query($conn,$sql)){
       file_put_contents("../images/$title.png",base64_decode($image));
       echo json_encode(array('response'=>"image uploaded"));
 }
 else{
         echo json_encode(array('response'=>"image uploaded"));

 }
  mysqli_close($conn);
 
}







































































/*header("Content-Type: application/json");

if($_SERVER['REQUEST_METHOD'] == 'POST')
{ 

$response = array();  
$title = isset($_POST['title']); 

$image = isset($_FILES['image_path']['name']);

$target = "C:/xampp/htdocs/AndroidRetrofit/images/".basename($image);



$fullPath = "https://bagory.000webhostapp.com/images/".$image;
$InsertSQL = "insert into images (image_path,title) values ('$fullPath','$title')";
 
 if(mysqli_query($conn, $InsertSQL)){
         
         
      $moved = move_uploaded_file(isset($_FILES['image_path'] ['name']), $target); 

     
             if( $moved ) {
              
           $success = true;  
           $message = "Successfully Uploaded";
           
            $_item = array( "success"=>$success ,"message"=>$message);	 
            array_push($response, $_item);
            echo json_encode($response);
       
       } else {
           $success = false;  
          $message = "not uploading";
      
       $_item = array( "success"=>$success ,"message"=>$message);	 
       array_push($response, $_item);
       echo json_encode($response);
       
       }
     
     
 }
 else
 {
      $success = false;  
      $message = "Error while uploading";
      
       $_item = array( "success"=>$success ,"message"=>$message);	 
       array_push($response, $_item);
       echo json_encode($response);

} 



}
;*/

?> 