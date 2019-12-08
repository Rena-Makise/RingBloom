(function($){
  $(function(){

    $('.sidenav').sidenav();
    $(".dropdown-trigger").dropdown({
        inDuration: 300,
        outDuration: 225,
        constrainWidth: false,
        hover: false,
        gutter: 0,
        coverTrigger: false,
        alignment: 'right'
    });
  }); // end of document ready
})(jQuery); // end of jQuery name space

// Your web app's Firebase configuration
var firebaseConfig = {

};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);