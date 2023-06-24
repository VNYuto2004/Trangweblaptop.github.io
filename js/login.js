$(document).ready(function(){
    $('#eye').click(function(){
        $(this).toggleClass('open');
        $(this).children('i').toggleClass('fa-eye-slash fa-eye');
        if($(this).hasClass('open')){
            $(this).prev().attr('type', 'text');
        }else{
            $(this).prev().attr('type', 'password');
        }
    });
    $('#form-login').submit(function() {
        if ($('#tendn').val() == '') {
            // alert('Tên đăng nhập không được để trống');
            $("#err").hide();
            $("#tendn").after("<span id='err' class='canhbao'>không được để trống!</span>");
            return false;
          }else if($('#matkhau').val()==''){
            //alert('Mật khẩu đăng nhập không được để trống');
            $("#err").hide();
            $("#matkhau").after("<span id='err' class='canhbao'>không được để trống!</span>");
            return false;
          }
    });
    $("#tendn"||"matkhau").click(function() {
        $("#err").hide();
      });
      var modal = document.getElementById('container_login');
      var span = document.getElementsByClassName("close")[0];
      span.onclick = function() {
      modal.style.display = "none";
     }

});
