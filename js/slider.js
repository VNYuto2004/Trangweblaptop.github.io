$(document).ready(function(){
    $('.slider').slick({
        slidesToShow : 1,
        prevArrow:
        "<button type='button' class='slick-prev slick-arrow'><i class='fa fa-angle-left' aria-hidden='true'></i></button>",

        nextArrow:
        "<button type='button' class='slick-next slick-arrow'><i class='fa fa-angle-right' aria-hidden='true'></i></button>",
        dots :true,
        autoplay: true,
        autoplaySpeed: 2000,
        responsive: [
            {
              breakpoint: 500,
              settings: {
                arrows: false,
              }
            },
          ]
    } );
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
    // lấy phần Modal
  var modal = document.getElementById('container_login');
  
  var form = document.getElementById('wrapper');

  // Lấy phần button mở Modal
  var btn = document.getElementById("button_dangnhap");

  // // Lấy phần span đóng Modal
  var span = document.getElementsByClassName("close")[0];

  // Khi button được click thi mở Modal
  btn.onclick = function() {
      modal.style.display = "block";
  }

  // Khi span được click thì đóng Modal
  span.onclick = function() {
      modal.style.display = "none";
  }

  // Khi click ngoài Modal thì đóng Modal
  window.onclick = function(event) {
      if (event.target == form) {
          modal.style.display = "none";
      }
  }
  });