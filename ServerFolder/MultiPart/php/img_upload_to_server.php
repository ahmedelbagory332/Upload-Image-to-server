<?php  


include "DatabaseConfig.php";



if($_SERVER['REQUEST_METHOD'] == 'POST')
{
   
   
    $target_dir = "../images/";  
    $target_file_name = $target_dir .basename($_FILES["image_path"]["name"]);  
    $response = array();  
    $title = $_POST['title']; 
    $img = $_FILES["image_path"]["name"];


   if (move_uploaded_file($_FILES["image_path"]["tmp_name"], $target_file_name))   
   {  
       $fullPath = "http://192.168.1.8/AndroidRetrofit/Base64/images/".$img;

       $InsertSQL = "insert into images (image_path,title) values ('$fullPath','$title')";
       
       if(mysqli_query($conn, $InsertSQL)){
        
        $success = true;  
        $message = "Successfully Uploaded";
        
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

  
?>  