json.array!(@annotations) do |annotation|
  json.extract! annotation, :recording_id, :time, :value
  json.url annotation_url(annotation, format: :json)
end
