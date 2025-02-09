//FETCH POST
async function fetchPost(url, data) {
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Định dạng dữ liệu là JSON
        },
        body: JSON.stringify(data), // Chuyển đổi dữ liệu thành chuỗi JSON
      });
  
      // Kiểm tra nếu có lỗi với response
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      // Chuyển đổi dữ liệu từ response sang JSON và trả về
        const result = await response.text();
        return result;
     
      
    } catch (error) {
      console.error('Error during fetch:', error);
    }
  }

    // Hàm gửi yêu cầu API bảo mật với tokens
async function fetchWithToken(url,token) {
    try {
      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,  // Thêm token vào header Authorization
          'Content-Type': 'application/json'
        }
      });
  
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      const result = await response.json(); // Chuyển dữ liệu từ response thành JSON
      console.log('Protected resource data:', result);
    } catch (error) {
      console.error('Error during fetch:', error);
    }
  }




function forwardTo(url){
    window.location.href = url;
}




async function register() {
    let fullName  = document.querySelector('#fullName').value;
    let username = document.querySelector('#username').value;
    let email = document.querySelector('#email').value;
    let phoneNumber = document.querySelector('#phoneNumber').value; 
    let passwordHash = document.querySelector('#password').value; 
    let confirmPassword = document.querySelector('#confirmPassword').value; 

    let url_create_user = 'http://localhost:8081/api/user/create/patient';

    console.log(fullName);

    //Check null các thông tin
    if(fullName="" || username==""|| email=="" || phoneNumber=="" || passwordHash=="" || confirmPassword==""){
        alert("Vui lòng điền đầy đủ thông tin");
        return;
    }
    //Sau khi điền đầy đủ, check pass and confirm
    if(passwordHash !== confirmPassword){
        console.log("Xác nhận sai ")
        document.querySelector('.error-pass').style.display = 'block';
        return; 
    }
    try{
        const data = {  username, email, phoneNumber, passwordHash,fullName };

        let result = await fetchPost(url_create_user, data);

        if (result) {
          
            alert("Đăng ký thành công");
            forwardTo('login.html');
            
           
        }
    }catch (error) {
        // Xử lý lỗi nếu có vấn đề khi gọi API
        console.error('Lỗi khi đăng nhập:', error);
        alert("Đã có lỗi xảy ra khi đăng nhập. Vui lòng thử lại sau.");
    }
}