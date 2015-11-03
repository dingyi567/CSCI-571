  <?php 
        
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

         
           if(isset($_GET["pageNumber"])){
              $url .="&paginationInput.pageNumber=";
              $url .=$_GET["pageNumber"];
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

            
                $url .="&outputSelector[1]=SellerInfo";
                $url .="&outputSelector[2]=PictureURLSuperSize";
                $url .="&outputSelector[3]=StoreInfo";
                //$url .="&paginationInput.pageNumber=1";// fenye!!!!!!!!!



    $xml=simplexml_load_file($url) or die("Error: Cannot create object");
    $array = json_decode(json_encode((array)$xml), TRUE);
    if (!empty($array) && $array['paginationOutput']['totalEntries'] != 0){
        $results = array(
            'ack' => (string)$array['ack'],
            'resultCount' => (string)$array['paginationOutput']['totalEntries'],
            'totalPages' => (string)$array['paginationOutput']['totalPages'],
            'pageNumber' => (string)$array['paginationOutput']['pageNumber'],
            'itemCount' => (string)$array['paginationOutput']['entriesPerPage']
        );
        
         $pageNum = (int)$array['paginationOutput']['pageNumber'];
         $resultCount = (int)$array['paginationOutput']['totalEntries'];
         $itemCount = (int)$array['paginationOutput']['entriesPerPage'];
         $totalPages = ceil($resultCount/$itemCount);
        
        if($pageNum == $totalPages){
           $tag = $itemCount - (($pageNum * $itemCount)-$resultCount);
        }
        else 
           $tag = $itemCount;

        
        for( $i = 0; $i < $tag; $i ++) {
            if($tag ==1)
                $items = $array['searchResult']['item'];
            else
                $items = $array['searchResult']['item'][$i];
            $item = 'item'.$i;
            
            $locations = "";
            for ($j = 0; $j < count($items['shippingInfo']['shipToLocations']); $j ++){
                $locations .= $items['shippingInfo']['shipToLocations'][$j];
                if ($j != count($items['shippingInfo']['shipToLocations']) - 1)
                    $locations .= ",";
            }
            $results[$item] = array (
    
                
                'sellerInfo' => array(
                    'sellerUserName' => (string)$items['sellerInfo']['sellerUserName'],
                    'feedbackScore' => (string)$items['sellerInfo']['feedbackScore'],
                    'positiveFeedbackPercent' => (string)$items['sellerInfo']['positiveFeedbackPercent'],
                    'feedbackRatingStar' => (string)$items['sellerInfo']['feedbackRatingStar'],
                    'topRatedSeller' => (string)$items['sellerInfo']['topRatedSeller'],
                    'sellerStoreName' => (string)$items['storeInfo']['storeName'],
                    'sellerStoreURL' => (string)$items['storeInfo']['storeURL']
                ),
                
                'basicInfo' => array (
                    'title' => (string)$items['title'],
                    'viewItemURL' => (string)$items['viewItemURL'],
                    'galleryURL' => (string)$items['galleryURL'],
                    'pictureURLSuperSize' => (string)$items['pictureURLSuperSize'],
                    'convertedCurrentPrice' => (string)$items['sellingStatus']['convertedCurrentPrice'],
                    'shippingServiceCost' => (string)$items['shippingInfo']['shippingServiceCost'],
                    'conditionDisplayName' => (string)$items['condition']['conditionDisplayName'],
                    'listingType' => (string)$items['listingInfo']['listingType'],
                    'location' => (string)$items['location'],
                    'categoryName' => (string)$items['primaryCategory']['categoryName'],
                    'topRatedListing' => (string)$items['topRatedListing']
                ),
                
                'shippingInfo' => array(
                    'shippingType' => (string)$items['shippingInfo']['shippingType'],
                    'shipToLocations' => $locations,
                    'expeditedShipping' => (string)$items['shippingInfo']['expeditedShipping'],
                    'oneDayShippingAvailable' => (string)$items['shippingInfo']['oneDayShippingAvailable'],
                    'returnsAccepted' => (string)$items['returnsAccepted'],
                    'handlingTime' => (string)$items['shippingInfo']['handlingTime']
                )
            );
        }
       
    }
    else{
        $results = array('ack'=> (string)"No results found");
    }

 echo json_encode($results, JSON_UNESCAPED_SLASHES);




    
    


    



       
?>
