var init = function(){
	$("[name='userLine']").dblclick(function(e) {
	  console.log(e.currentTarget.id);
	  //debugger;
	  var frame = $("#frame");
	  if(frame){
		frame.prop( "hidden", null );
		frame.prop( "src", "/chat/private/" + e.currentTarget.id);
	  } 
	});
};
document.addEventListener("DOMContentLoaded", init);