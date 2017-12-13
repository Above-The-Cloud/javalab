<?php

/**
* 本地测试数据库初始化！！！
* 只需运行一次
* 多次运行会删除原有数据库！！！
* 环境：wamp default
* by:汪春雨
*/



$dbhost = 'localhost:3306';  // mysql服务器主机地址
$dbuser = 'root';            // mysql用户名
$dbpass = '';          // mysql用户名密码
$conn = mysqli_connect($dbhost, $dbuser, $dbpass);

//连接数据库；
if(! $conn )
{
  die('连接错误: ' . mysqli_error($conn));
}
echo '连接成功<br />';
mysqli_query($conn , "set names utf8");

$retval = mysqli_select_db($conn, 'Chess' );
if($retval) 
{
	$sql = 'DROP DATABASE Chess';
	$res = mysqli_query( $conn, $sql );
	if(! $res )
	{
    	die('原始删除数据库失败: ' . mysqli_error($conn));
	}
	echo "原始数据库删除成功\n","<br>";
}

$sql = 'CREATE DATABASE Chess';
$retval = mysqli_query($conn,$sql );
if(! $retval )
{
    die('创建数据库失败: ' . mysqli_error($conn));
}
echo "数据库 Chess 创建成功\n","<br>";

mysqli_select_db($conn, 'Chess' );

//创建表user_info
$sql = "CREATE TABLE user_info( ".
        "user_id INT UNSIGNED NOT NULL AUTO_INCREMENT, ".
        "user_name VARCHAR(100) NOT NULL, ".
        "user_password VARCHAR(40) NOT NULL, ".
        "num_win INT NOT NULL,".
        "num_lose INT NOT NULL,".
        "num_peace INT NOT NULL,".
        "submission_time DATETIME, ".
        "PRIMARY KEY ( user_id ))ENGINE=InnoDB DEFAULT CHARSET=utf8; ";
$retval = mysqli_query($conn,$sql );
if(! $retval )
{
    die('数据表创建失败: ' . mysqli_error($conn));
}
echo "数据表 user_info 创建成功\n","<br>";

//插入初始测试数据
//$user_id = 10152150127;
$user_password = '123456';
$user_name = '壹汪春雨';
//$submission_time = '2017-12-03 12:00:00';
$submission_time = date("Y-m-d h:m:s");
$num_win = 0;
$num_lose = 0;
$num_peace = 0;
$sql = "INSERT INTO user_info ".
        "(user_name, user_password, num_win, num_lose, num_peace, submission_time)".
        "VALUES".
        "('$user_name', '$user_password', '$num_win', '$num_lose', '$num_peace', '$submission_time')";
$retval = mysqli_query( $conn, $sql );
if(! $retval )
{
  die('无法插入数据: ' . mysqli_error($conn));
}
echo "数据插入成功\n","<br>";


mysqli_close($conn);
?>
