/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {

});
//function getURLParameter(name) {
//    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
//}
function uncheckOthers(radio) {
    var name = radio.name.substring(radio.name.lastIndexOf(':'));
    var elements = radio.form.elements;
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].name.substring(elements[i].name.lastIndexOf(':')) == name) {
            elements[i].checked = false;
            console.log(elements[i].name+" "+elements[i].checked);
        }
    }
    radio.checked = true;
    console.log(radio.name+"  "+radio.checked);
}
//function selectAndClose() {
//    //opener.document.forms[formId][fieldId].value = value;
//    //
//    //opener.document.forms[formId][fieldId].value = opener.document.forms["pre_orientation_popup_form"]["pre_orientation_popup_form:pre_orientation_popup_preId"].value;
//    //window.close();
//    alert(document.getElementById("pre_orientation_popup_form:pre_orientation_popup_preId").value);
//}
//function closePage(){
//    window.close();
//}