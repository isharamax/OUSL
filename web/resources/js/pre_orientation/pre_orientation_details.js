/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    // $("#pre_orientation_details_form\\:pre_orientation_details_date_input").attr("disabled", true);
    //setEditability(true);
    disableFields();
    //$("#pre_orientation_details_form\\:pre_orientation_details_btn_new").click(function(e) {
    //    e.preventDefault();
//        $("#pre_orientation_details_form\\:pre_orientation_details_program").attr('disabled', false);
//        $("#pre_orientation_details_form\\:pre_orientation_details_center_list").removeAttr("disabled");
//        $("#pre_orientation_details_form\\:pre_orientation_details_date_input").attr("disabled", false);
//        $("#pre_orientation_details_form\\:pre_orientation_details_session").attr('disabled', false);
    //    setEditability(false);
    //    return false;
   // });
//    $("#pre_orientation_details_form\\:pre_orientation_details_btn_delete").click(function(e) {
//        e.preventDefault();
//        window.open("pre_orientation_popup.xhtml", "selectWindow", "status,width=400,height=300");
//        //ondlg.show();
//        //dlg.show();
//        return false;
//    });
    
//    $("#pre_orientation_details_form\\:pre_orientation_details_btn_submit").click(function(e) {
//        e.preventDefault();
//        return false;
//    });
//    $("#pre_orientation_details_form\\:pre_orientation_details_btn_delete").click(function(e) {
//        e.preventDefault();
//        return false;
//    });
});
function disableFields(){
     $("#pre_orientation_details_form\\:pre_orientation_details_staff_no").attr('disabled', true);
    $("#pre_orientation_details_form\\:pre_orientation_details_name").attr('disabled', true);
    $("#pre_orientation_details_form\\:pre_orientation_details_department").attr('disabled', true);
}
function setEditability(n) {
    $("#pre_orientation_details_form\\:pre_orientation_details_program").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_center_list").attr("disabled", n);
    $("#pre_orientation_details_form\\:pre_orientation_details_date_input").attr("disabled", n);
    $("#pre_orientation_details_form\\:pre_orientation_details_session").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_current_level").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_point_level").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_staff_no").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_name").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_department").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_attended_program").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_attended_center").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_date").attr('disabled', n);
    $("#pre_orientation_details_form\\:pre_orientation_details_attended_session").attr('disabled', n);
}
function openWin(url)
{
    window.open(url,"selectWindow","status,width=400,height=300");

}
