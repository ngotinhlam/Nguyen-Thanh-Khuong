<?php
error_reporting(0);
class database {

    private $hostname = 'localhost';
    private $username = 'root';
    private $password = '';
    private $dbname = 'banhoa';
    private $conn = NULL;
    private $result = NULL;

    private function connect() {
        $this->conn = mysqli_connect($this->hostname, $this->username, $this->password, $this->dbname) or die('Không thể kết nối tới CSDL');
        mysqli_query($this->conn, "SET NAMES 'utf8'");
    }
    
    private function disconnect() {
        if ($this->conn) {
            mysqli_close($this->conn);
        }
    }
    
    public function query($sql) {
        $this->connect();
        if ($this->conn && isset($sql)) {
            $this->result = mysqli_query($this->conn, $sql) or die('Không thể truy vấn');
        }
        $this->disconnect();
    }
    
    public function count_num_rows() {
        if ($this->result && mysqli_num_rows($this->result)>0) {
            return mysqli_num_rows($this->result);
        } else {
            return 0;
        }
    }
    
    public function fetch() {
        if ($this->count_num_rows()>0) {
            return mysqli_fetch_assoc($this->result);
        } else {
            return 0;
        }
    }
    
    public function excute($sql) {
        $this->connect();
        if ($this->conn && isset($sql)) {
            mysqli_query($this->conn, $sql) or die('Không thể thực thi lệnh');
        }
        $this->disconnect();
    }
}


?>
