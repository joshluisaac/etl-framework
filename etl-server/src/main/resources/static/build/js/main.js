/**
 * 
 */
var idregex = /^(.+?)(\d+)$/i;
var imagePath="../build/images";

function ClearForm(){
    ButtonSelect(4);
    $("#mainform")[0].reset();
    $(".help-block").hide();
    $(".form-group").removeClass('has-error');
}


function ButtonSelect(buttonNum){
    switch (buttonNum)
    {
        case 2:
            $("#register").hide();
            $("#change").show();
            $("#delete").hide();
            $("#cancel").show();
            break;
        case 3:
            $("#register").hide();
            $("#change").hide();
            $("#delete").show();
            $("#cancel").show();
            break;
        case 4:
            $("#register").show();
            $("#change").hide();
            $("#delete").hide();
            $("#cancel").hide();
            break;
    }
}

function changeFormInfo(identifier,action)
{
    var row  = document.getElementById("tr_"+identifier);
    var elements = document.getElementById("mainform").elements;
    //alert(elements.length);
    ButtonSelect(action);
    if(elements[0].type=="hidden")
        elements[0].value = identifier;

    var radioName="";
    var j=0;
    var isDontFill=false;
    var isNewRadio="";
    var isRadioAsigned=false;
    var cellContent="";
    for (i=1; i<elements.length;i++)
    {
        cellContent=row.cells[j].innerHTML.trim();
//         alert("element type ="+elements[i].type +"  value=  "+cellContent );

        if(elements[i].type!="button" && elements[i].type!="hidden" && elements[i].name!="dontFill")
        {
            //alert(cellContent);
            if(elements[i].type=="radio")
            {
                if (elements[i].name!=isNewRadio)
                {

                    if (isNewRadio!="" && isRadioAsigned==false )
                        ++j;
                    isNewRadio=elements[i].name;
                    isRadioAsigned=false;
                }

                //alert(elements[i].value +" == "+ cellContent)
                if (elements[i].value == cellContent)
                {
                    //alert("element type ="+elements[i].type +"  value=  "+cellContent );
                    elements[i].checked="checked";
                    isRadioAsigned=true;
                    ++j;
                }
                if (elements[i+1].type!="radio" && isRadioAsigned==false)
                    ++j
            }
            else
            {

                if (cellContent == "" && elements[i].type=="select-one")
                {
                    //alert("man injam");
                    elements[i].value = "NULL";
                }
                if (elements[i].type=="select-one")
                {
                    // alert(cellContent.trim());
                    // alert(elements[i].id);
                    // alert($("#"+elements[i].id+" option:contains('Yes')").val());
                    // alert($("#"+elements[i].id+" option:contains('"+cellContent+"')").val());
                    $("#"+elements[i].id).val($("#"+elements[i].id+" option:contains('"+cellContent+"')").val());
                }
                else {

                    elements[i].value = cellContent;

                }
                ++j;
                if (elements[i].onchange)
                {
                    elements[i].onchange();
                }

            }
        }

    }
}




