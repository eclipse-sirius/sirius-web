export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
}
export interface GQLRepresentation {
  id: string;
}
export interface Chart extends GQLRepresentation {
  metadata: GQLRepresentationMetadata;
}

export interface BarChart extends Chart {
  entries: BarChartEntry[];
}

export interface BarChartEntry {
  key: string;
  value: number;
}
