json.array!(@locations) do |location|
  json.extract! location, :recording_id, :time, :longitude, :latitude, :altitude, :speed, :bearing, :accuracy
  json.url location_url(location, format: :json)
end
