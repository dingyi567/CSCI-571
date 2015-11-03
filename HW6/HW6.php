<!DOCTYPE html>
<html>
    <head>
        <title>ebay shopping</title>
        <style>
            html {
                overflow-y: scroll;
                overflow-x: scroll;
            }
            body{
                 position: relative;
                 top:10px;
                 left: 200px;
                 padding: 20px 10px 10px;                
            }
            img{
                position: relative;
                right: -360px; 
            }
            p1{
                font: bold 25pt Verdana, Geneva, sans-serif; 
                position: relative;
                top: -40px;   
                right: -350px;
            }
            p2{
                font: bold 25pt Verdana, Geneva, sans-serif; 
            }
            form{
                border: 2px solid;
                padding: 10px 10px 10px 10px;
                /*font: bold 15pt Verdana, Geneva, sans-serif;*/ 
                position: relative;
                left: 200px;
                width: 800px;
            }
            th{
                text-align: left;
                vertical-align: top;
                border-bottom: 1px grey solid;   
                font: bold 15pt Verdana, Geneva, sans-serif; 
            }
            td{
                
                border-bottom: 1px grey solid;
                font: 15pt Verdana, Geneva, sans-serif; 
            }
            #clear, #search{
                position:relative;
                right:-600px;
            }    
            .results{
                font: bold 25pt Verdana, Geneva, sans-serif; 
            }
            #topRatedListing{
                width: 120px;
                height: 100px;
                position:relative;
                right:-300px;
                top:-20px;
            
            }
            #resultsTable{
                border: 2px solid grey;
                padding: 10px 20px 20px;
                font: 15pt Verdana, Geneva, sans-serif; 
                /*table-layout: fixed;*/
            }
            .resultsImage{
                position:relative;
                right: -20px;
                top: 70px;
                width: 300px;
                height: 300px;
            }
            span.italic{
               font-style: italic;
            }
            span.bold{
               font-weight: bold;
            }
            span.resultsNumber{
               font: bold 15pt Verdana, Geneva, sans-serif; 
               position: relative;
               right: -450px;
            }
            #resultsTh{
                width:400px;
                height: 400px;
            }

            

        </style>   
        
        <script language="javascript">
            
            //function isNumber(n) { return /^-?[\d.]+(?:e-?\d+)?$/.test(n); } 
            function isNumber(n) { return /^-?\d+(\.\d+)?$/.test(n); } 
            
            function validateForm(){
                //Key Words validation
                var d = document.forms["myForm"]["keyWords"].value;
                var keyWords = d.trim();
                document.forms["myForm"]["keyWords"].value = keyWords;
                if (/^\s*$/.test(keyWords)){
                    document.forms["myForm"]["keyWords"].value = "";
                    keyWords = "";
                }
                if (keyWords == "" || keyWords == null){
                    alert("Please enter words for Key Words.");
                    return false;
                }
                //minPrice and maxPrice validation
                var a = document.forms["myForm"]["minPrice"].value;
                var b = document.forms["myForm"]["maxPrice"].value;  
                var minPrice = a.trim();
                var maxPrice = b.trim();
                document.forms["myForm"]["minPrice"].value = minPrice;
                document.forms["myForm"]["maxPrice"].value = maxPrice;
                
                if (/^\s*$/.test(minPrice)){
                    document.forms["myForm"]["minPrice"].value = "";
                    minPrice = "";
                }
                if (/^\s*$/.test(maxPrice)){
                    document.forms["myForm"]["maxPrice"].value = "";
                    maxPrice = "";
                }
                if(minPrice!="" && minPrice!=null){
                   if (!(isNumber(minPrice))){
                     alert("Please enter numbers for Price Range."); 
                     return false;                         
                   } 
                   minPrice=parseFloat(minPrice);
                   if (minPrice<0){
                     alert("Price should not be less than zero.");
                     return false;                        
                   }              
                }                
                if(maxPrice!="" && maxPrice!=null){
                   if (!(isNumber(maxPrice))){
                     alert("Please enter numbers for Price Range."); 
                     return false;                         
                   } 
                   maxPrice=parseFloat(maxPrice);
                   if (maxPrice<0){
                     alert("Price should not be less than zero.");
                     return false;                        
                   }              
                }
                if(minPrice!="" && minPrice!=null&&maxPrice!="" && maxPrice!=null){
                   if (minPrice > maxPrice){
                     alert("Minimum price should be less than maximum price.");
                     return false; 
                   } 
                }             
                
                // max handling time validation
                var c = document.forms["myForm"]["maxHandlingTime"].value;
                var maxHandlingTime = c.trim();
                document.forms["myForm"]["maxHandlingTime"].value = maxHandlingTime;
                if (/^\s*$/.test(maxHandlingTime)){
                    document.forms["myForm"]["maxHandlingTime"].value = "";
                    maxHandlingTime = "";
                }
                if(maxHandlingTime!=""&&maxHandlingTime!=null){
                    if(!isNumber(maxHandlingTime)){
                        alert("Please enter numbers for Max Handling Time."); 
                        return false; 
                    }
                    if(maxHandlingTime<1){
                        alert("Max Handling Time should not be less than 1.");
                        return false;
                    }
                }
         
            } 
            
            function clearForm(){
                var i;
                var formItems= document.forms["myForm"];             
                for (i=0; i<formItems.length; i++){                   
                    field_type = formItems[i].type.toLowerCase();
                    switch (field_type)
                    {
                    case "text":
                        formItems[i].value = "";
                        break;
                    case "checkbox":
                        if (formItems[i].checked)
                        {
                            formItems[i].checked = false;
                        }
                        break;
                    case "select-one":
                        formItems[i].selectedIndex = 0;
                        break;
                    default:
                        break;
                    }                
               } 
                document.getElementById("resultsArea").innerHTML="";
            }
        </script>
        
        <?php 
            function generateURL()
            {
              $i=0;
              $url = "http://svcs.eBay.com/services/search/FindingService/v1?siteid=0&SECURITY-APPNAME=USC5a6180-11e2-417a-858c-fba0e6484b1&OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&RESPONSE-DATA-FORMAT=XML";
            if(isset($_GET["keyWords"])){
              $url .= "&keywords=";
              $url .= urlencode($_GET["keyWords"]);
            } 

            if(isset($_GET["resultsPerPage"])){
              $url .="&paginationInput.entriesPerPage=";
              $url .=$_GET["resultsPerPage"];
            }

            if(isset($_GET["sortBy"])){
             $url .= "&sortOrder=";
             if($_GET["sortBy"]=="Best Match")
                $url .= "BestMatch";
             else if($_GET["sortBy"]=="Price: highest first")
                $url .= "CurrentPriceHighest";   
             else if($_GET["sortBy"]=="Price + Shipping: highest first")
                $url .= "PricePlusShippingHighest";
             else if($_GET["sortBy"]=="Price + Shipping: lowest first")
                $url .= "PricePlusShippingLowest";
            }
            
            if($_GET["minPrice"]!=""){
               // echo "AAAAA:" . $_GET["minPrice"];
              $url .="&itemFilter[$i].name=MinPrice&itemFilter[$i].value=";
              $url .=$_GET["minPrice"];
              $i += 1;
            }

            if($_GET["maxPrice"]!=""){
              // echo "BBBBB:" . $_GET["maxPrice"];
              $url .="&itemFilter[$i].name=MaxPrice&itemFilter[$i].value=";
              $url .=$_GET["maxPrice"];
              $i += 1;
            }
            
                if(isset($_GET["new"])||isset($_GET["used"])||isset($_GET["veryGood"])||isset($_GET["good"])||isset($_GET["acceptable"])){
            $j=0;
            $url .="&itemFilter[$i].name=Condition";
            if(isset($_GET["new"])){
              $url .="&itemFilter[$i].value[$j]=1000";
              $j += 1; 
            }
            if(isset($_GET["used"])){
              $url .="&itemFilter[$i].value[$j]=3000";
              $j += 1; 
            }
            if(isset($_GET["veryGood"])){
              $url .="&itemFilter[$i].value[$j]=4000";
              $j += 1; 
            }
            if(isset($_GET["good"])){
              $url .="&itemFilter[$i].value[$j]=5000";
              $j += 1; 
            }
            if(isset($_GET["acceptable"])){
              $url .="&itemFilter[$i].value[$j]=6000";
              $j += 1; 
            }
            $i += 1;
         
         }


          if(isset($_GET["buyItNow"])||isset($_GET["auction"])||isset($_GET["classifiedAds"])){
            $j=0;
            $url .="&itemFilter[$i].name=ListingType";
            if(isset($_GET["buyItNow"])){
              $url .="&itemFilter[$i].value[$j]=FixedPrice";
              $j += 1; 
            }
            if(isset($_GET["auction"])){
              $url .="&itemFilter[$i].value[$j]=Auction";
              $j += 1; 
            }
            if(isset($_GET["classifiedAds"])){
              $url .="&itemFilter[$i].value[$j]=Classified";
              $j += 1; 
            }
            $i += 1;
          }

            if(isset($_GET["returnAccepted"])){
              $url .="&itemFilter[$i].name=ReturnsAcceptedOnly&itemFilter[$i].value=true";
              $i += 1;
            }

            if(isset($_GET["freeShipping"])){
              $url .="&itemFilter[$i].name=FreeShippingOnly&itemFilter[$i].value=true";
              $i += 1;
            }
            if(isset($_GET["expeditedShipping"])){
              $url .="&itemFilter[$i].name=ExpeditedShippingType&itemFilter[$i].value=Expedited";
              $i += 1;
            }
            if($_GET["maxHandlingTime"]!=""){
              $url .="&itemFilter[$i].name=MaxHandlingTime&itemFilter[$i].value=";
              $url .=floor($_GET["maxHandlingTime"]);
              $i += 1;
            }
            
            return $url;
            }
?>
        
    </head>
    
    <body>
        <img src="http://cs-server.usc.edu:45678/hw/hw6/ebay.jpg">
        <p1>Shopping</p1>
        <form name="myForm" method="get" onsubmit="return validateForm()" >
            <table>
                <tr><th>Key Words*:</th> 
                    <td><input name="keyWords" type=text size="75" 
                               value="<?php if(isset($_GET['keyWords'])) echo $_GET['keyWords'];?>"> </td></tr>               
                <tr><th>Price Range:</th> 
                    <td>from $<input name="minPrice" type=text size="10" 
                                                            <?php if(isset($_GET["minPrice"]))  echo "value=" . $_GET["minPrice"]  ?>> 
                    to $<input name="maxPrice" type=text size="10" 
                               <?php if(isset($_GET["maxPrice"]))  echo "value=" . $_GET["maxPrice"]  ?>></td></tr>             
                <tr><th>Condition:</th> 
                    <td><input type="checkbox" name="new" 
                               <?php if(isset($_GET["new"])) echo "checked='checked'"; ?>> New
                           <input type="checkbox" name="used" 
                                  <?php if(isset($_GET["used"])) echo "checked='checked'"; ?>> Used
                           <input type="checkbox" name="veryGood" 
                                  <?php if(isset($_GET["veryGood"])) echo "checked='checked'"; ?>> Very Good
                           <input type="checkbox" name="good" 
                                  <?php if(isset($_GET["good"])) echo "checked='checked'"; ?>> Good
                           <input type="checkbox" name="acceptable" 
                                  <?php if(isset($_GET["acceptable"])) echo "checked='checked'"; ?>> Acceptable</td></tr>
                <tr><th>Buying formats:</th> 
                           <td><input type="checkbox" name="buyItNow" 
                                      <?php if(isset($_GET["buyItNow"])) echo "checked='checked'"; ?>> Buy It Now
                           <input type="checkbox" name="auction" 
                                  <?php if(isset($_GET["auction"])) echo "checked='checked'"; ?>> Auction
                           <input type="checkbox" name="classifiedAds" 
                                  <?php if(isset($_GET["classifiedAds"])) echo "checked='checked'"; ?>> Classified Ads</td></tr>
                <tr><th>Seller:</th>
                           <td><input type="checkbox" name="returnAccepted" 
                                      <?php if(isset($_GET["returnAccepted"])) echo "checked='checked'"; ?>> Return accepted</td></tr>
                <tr><th>Shipping:</th>
                           <td><input type="checkbox" name="freeShipping" 
                                      <?php if(isset($_GET["freeShipping"])) echo "checked='checked'"; ?>> Free Shipping<br>
                           <input type="checkbox" name="expeditedShipping" 
                                  <?php if(isset($_GET["expeditedShipping"])) echo "checked='checked'"; ?>> Expedited shipping available<br>                            
                        Max handling time(days):
                              <input type="text" name="maxHandlingTime" size="10" 
                                     <?php if(isset($_GET["maxHandlingTime"]))  echo "value=" . $_GET["maxHandlingTime"]  ?>></td></tr>
            
                <tr><th>Sort by:</th> <td>
                         <select name="sortBy" id="sortBy">
                           <option selected>Best Match</option>
                           <option>Price: highest first</option>
                           <option>Price + Shipping: highest first</option>
                           <option>Price + Shipping: lowest first</option>
                         </select></td></tr>
                
                        <script type="text/javascript">
                             document.getElementById("sortBy").value = "<?php echo $_GET["sortBy"];?>";
                        </script>
                
                <tr><th> Results Per Page:</th> <td>
                         <select name="resultsPerPage" id="resultsPerPage">
                           <option selected>5</option>
                           <option>10</option>
                           <option>15</option>
                           <option>20</option>
                         </select></td></tr>
                       
                         <script type="text/javascript">
                             document.getElementById("resultsPerPage").value = "<?php echo $_GET["resultsPerPage"];?>";
                         </script>
                
            </table>
            <input type="button" value="clear" name="clear" id="clear" onclick="clearForm()">
            <input type="submit" value="search" name="search" id="search">             
        </form>
        
<?php if(isset($_GET["search"])):?>
<?php $URL = generateURL(); ?>
      
<?php  
  //echo $URL;
  echo "<div id='resultsArea'>";
  $xml = simplexml_load_file($URL) or die("Error: Cannot create object");
  if ($xml->paginationOutput->totalEntries == 0)
     echo "<span class='resultsNumber'>No results found</span>";
  else {
      echo "<span class='resultsNumber'>", $xml->paginationOutput->totalEntries, " Results for " , "<span class='italic'>", $_GET["keyWords"], "</span></span>";
     echo "<table id='resultsTable'>";
     foreach($xml->searchResult->item as $items){
        echo "<tr><th id='resultsTh'> <img  class='resultsImage' src=$items->galleryURL></th>";
        echo "<td><br> <a href =$items->viewItemURL>$items->title</a><br>";

         if($items->topRatedListing=="true") echo "<br>";
         
         echo "<span class='bold'>Condition: </span>";
         if($items->condition->conditionId=="1000") echo "New";
         else if($items->condition->conditionId=="3000") echo "Used";
         else if($items->condition->conditionId=="4000") echo "Very Good";
         else if($items->condition->conditionId=="5000") echo "Good";
         else if($items->condition->conditionId=="6000") echo "Acceptable";
         else  echo $items->condition->conditionDisplayName;
         echo "<br>";

        if($items->topRatedListing=="true"){
           echo "<img id='topRatedListing' src='http://cs-server.usc.edu:45678/hw/hw6/itemTopRated.jpg'> <br>";
        }

        
        echo "<span class='bold'>";
        if($items->listingInfo->listingType=="FixedPrice" || $items->listingInfo->listingType=="StoreInventory")
           echo "<br>Buy It Now<br>";
        else if($items->listingInfo->listingType=="Auction")
           echo "<br>Auction<br>";
        else if($items->listingInfo->listingType=="Classified")
           echo "<br>Classified Ad<br>";
        else echo $items->listingInfo->listingType;
        echo "</span>";
         
        if($items->returnsAccepted)
           echo "<br>Seller Accepts return<br>";
         
 
        if((isset($items->shippingInfo->shippingServiceCost) && $items->shippingInfo->shippingServiceCost==0.0)||$items->shippingInfo->shippingType=="FREE"){
            echo "FREE Shipping";
            if($items->shippingInfo->expeditedShipping=="true")
               echo " -- Expedited Shipping Available";
            echo " -- Handled for shipping in ", $items->shippingInfo->handlingTime, " day(s) <br>";
            echo "<br><br><span class='bold'>Price:", "$", $items->sellingStatus-> convertedCurrentPrice, "</span>";
            echo "  <span class='italic'>From    ", $items->location, "</span>";
            echo "</td></tr>"; 
        }        
        else{           
           echo "Shipping Not FREE";
           if($items->shippingInfo->expeditedShipping=="true")
               echo " -- Expedited Shipping Available";

           echo " -- Handled for shipping in ", $items->shippingInfo->handlingTime, " day(s) <br>";
           echo "<br><br><span class='bold'>Price:", "$", $items->sellingStatus-> convertedCurrentPrice, "</span>";
         
           if(isset($items->shippingInfo->shippingServiceCost) && $items->shippingInfo->shippingServiceCost>0){
              echo "<span class='bold'>(+", "$", $items->shippingInfo->shippingServiceCost, " for shipping)", "</span>";
           }
           echo "  <span class='italic'>From    ", $items->location, "</span>";
           echo "</td></tr>";        
     }
     }
     echo "</table>";
  }
     echo "</div>";
  
?>
        
        
 <?php endif; ?>
    </body>
</html>


