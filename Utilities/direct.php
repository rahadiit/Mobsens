<?php
	function mst($mst)
	{
		$ts = $mst / 1000;
		$ms = $mst % 1000;
		
		return date("Y-m-d", $ts)."T".date("H:i:s.", $ts).str_pad("$ms", 3, "0", STR_PAD_LEFT)."Z";
	}
	
	function cna($a)
	{
		if(!is_array($a)) return $a;
		
		$ra = array();
		
		foreach($a as $k => $v)
		{
			if($k === null || $v === null) continue;
			
			$ra[$k] = cna($v);
		}
		
		return $ra;
	}
	
	$object = array(
		"recording" => array(
			"device_id" => 0,
			"accelerometers_attributes" => array(),
			"annotations_attributes" => array(),
			"batteries_attributes" => array(),
			"devices_attributes" => array(),
			"gravities_attributes" => array(),
			"gyroscopes_attributes" => array(),
			"lights_attributes" => array(),
			"linear_accelerations_attributes" => array(),
			"locations_attributes" => array(),
			"magnetic_fields_attributes" => array(),
			"pressures_attributes" => array(),
			"proximities_attributes" => array(),
			"rotation_vectors_attributes" => array()));
		
	
	foreach(explode(PHP_EOL, $HTTP_RAW_POST_DATA) as $line)
	{
		$entry = json_decode($line, true);
		
		if(array_key_exists("rec", $entry))
		{
			$object["recording"]["device_id"] = $entry["rec"]["did"];
		}
		else if(array_key_exists("annotation", $entry))
		{
			$object["recording"]["annotations_attributes"][] = cna(array(
				"time" => mst($entry["annotation"]["time"]),
				"value" => $entry["annotation"]["value"]));
				
		}
		else if(array_key_exists("connectivity", $entry))
		{
			// NOP
		}
		else if(array_key_exists("location", $entry))
		{
			$object["recording"]["locations_attributes"][] = cna(array(
				"time" => mst($entry["location"]["time"]),
				"longitude" => $entry["location"]["longitude"],
				"latitude" => $entry["location"]["latitude"],
				"speed" => $entry["location"]["speed"],
				"bearing" => $entry["location"]["bearing"],
				"accuracy" => $entry["location"]["accuracy"]));
		}
		else if(array_key_exists("sensor", $entry))
		{
			$ft = null;
			$xt = null;
			$yt = null;
			$zt = null;
			
			switch($entry["sensor"]["sensor"])
			{
				case 1:
					$ft = "accelerometers_attributes";
					$xt = "x";
					$yt = "y";
					$zt = "z";
					break;
					
				case 9:
					$ft = "gravities_attributes";
					$xt = "x";
					$yt = "y";
					$zt = "z";
					break;
					
				case 4:
					$ft = "gyroscopes_attributes";
					$xt = "rX";
					$yt = "rY";
					$zt = "rZ";
					break;
					
				case 5:
					$ft = "lights_attributes";
					$xt = "ambientLight";
					break;
					
				case 10:
					$ft = "linear_accelerations_attributes";
					$xt = "x";
					$yt = "y";
					$zt = "z";
					break;
					
				case 2:
					$ft = "magnetic_fields_attributes";
					$xt = "x";
					$yt = "y";
					$zt = "z";
					break;
					
				case 6:
					$ft = "pressures_attributes";
					$xt = "pressure";
					break;
					
				case 8:
					$ft = "proximities_attributes";
					$xt = "proximity";
					break;
					
				case 11:
					$ft = "rotation_vectors_attributes";
					$xt = "xSinTheta";
					$yt = "ySinTheta";
					$zt = "zSinTheta";
					break;
			}
			
			if($ft != null)
			{
				$object["recording"][$ft][] = cna(array(
					"time" => mst($entry["sensor"]["time"]),
					$xt => doubleval($entry["sensor"]["values"][0]),
					$yt => doubleval($entry["sensor"]["values"][1]),
					$zt => doubleval($entry["sensor"]["values"][2])));
			}
		}
	}
	
	$json = json_encode($object);
	$json_length = strlen($json);
	
	$context  = stream_context_create(array(
		"http" => array(
			"method"  => "POST",
			"header"  => "Content-type: application/json\r\nContent-Length: $json_length",
			"content" => $json)));

	echo file_get_contents('http://mobilesensing.west.uni-koblenz.de:3000/recordings', false, $context);
	
?>