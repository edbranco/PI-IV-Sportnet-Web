 $(document).ready(function () {
$(".menu>ul").addClass("js");
      $(".menu>ul").addClass("js").before('<div class="atalho">MENU</div>');

      $(".atalho").click(function(){
        $(".menu>ul").toggle();
      });

      $(window).resize(function(){
        if(window.innerWidth > 994) {
          $(".menu>ul").removeAttr("style");
        }
      });
  });