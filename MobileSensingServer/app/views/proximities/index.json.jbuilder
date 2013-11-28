json.array!(@proximities) do |proximity|
  json.extract! proximity, :recording_id, :time, :proximity
  json.url proximity_url(proximity, format: :json)
end
