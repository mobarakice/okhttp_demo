# okhttp_demo
Okhttp tutorial


This is my server code#1:

<?php
   if(isset($_FILES['userFile'])){
      $errors= array();
      $file_name = $_FILES['userFile']['name'];
     // $file_size =$_FILES['userFile']['size'];
      $file_tmp =$_FILES['userFile']['tmp_name'];
	  $file_type=$_FILES['userFile']['type'];
      
	  
	  /*$file_ext=strtolower(end(explode('.',$_FILES['userFile']['name'])));
      
      $expensions= array("jpeg","jpg","png");
      
      if(in_array($file_ext,$expensions)=== false){
         $errors[]="extension not allowed, please choose a JPEG or PNG file.";
      }
      
      if($file_size > 2097152){
         $errors[]='File size must be excately 2 MB';
      }*/
	  
	  
      
      if(empty($errors)==true){
		  if($file_type == "image/*"){
			  move_uploaded_file($file_tmp,"Images/".$file_name);
		  }if($file_type == "video/*"){
			  move_uploaded_file($file_tmp,"Videos/".$file_name);
		  }if($file_type == "audio/*"){
			  move_uploaded_file($file_tmp,"Music/".$file_name);
		  }if($file_type == "application/vnd.android.package-archive"){
			  move_uploaded_file($file_tmp,"Apps/".$file_name);
		  }else{
			  move_uploaded_file($file_tmp,"Files/".$file_name);
		  }  
         echo "Success";
      }else{
         print_r($errors);
      }
   }
?>


code#2:
<?php
  $filename=$_GET['fileName'];
  $fileData=file_get_contents('php://input');
  $fhandle=fopen($filename, 'wb');
  fwrite($fhandle, $fileData);
  fclose($fhandle);
  echo("Upload completed");
?>

PHP allowed to upload only 2MB files. If you want to upload large file, 
then you need to change php.ini file(post_max_size, upload_max_filesize, and max_execution_time,memory_limit) or chunking.
