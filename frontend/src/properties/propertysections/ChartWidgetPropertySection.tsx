/*******************************************************************************
 * Copyright (c) 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
// SBE: This comment will be removed in the future but we should not have to wait to solve
// all d3 relatd typing issues in order to move forward with our TypeScript checks
// @ts-nocheck
import { BarChart, Chart, PieChart } from 'charts/Charts.types';
import * as d3 from 'd3';
import {
  BarChartProps,
  ChartWidgetPropertySectionProps,
  PieChartProps,
} from 'properties/propertysections/ChartWidgetPropertySection.types';
import React, { useEffect, useRef } from 'react';
import { PropertySectionLabel } from './PropertySectionLabel';

/**
 * Defines the content of a Link property section.
 */
export const ChartWidgetPropertySection = ({ widget, subscribers }: ChartWidgetPropertySectionProps) => {
  const { chart } = widget;
  let chartComponent: JSX.Element;
  if (isBarChart(chart)) {
    chartComponent = <BarChartComponent width={500} height={250} chart={chart} />;
  } else if (isPieChart(chart)) {
    chartComponent = <PieChartComponent width={300} height={300} chart={chart} />;
  }
  let content;
  if (chartComponent) {
    content = (
      <>
        <PropertySectionLabel label={widget.label} subscribers={subscribers} />
        {chartComponent}
      </>
    );
  }
  return <div>{content}</div>;
};

const isBarChart = (chart: Chart): chart is BarChart => {
  return chart.metadata?.kind === 'BarChart';
};

const isPieChart = (chart: Chart): chart is PieChart => {
  return chart.metadata?.kind === 'PieChart';
};

const BarChartComponent = ({ width, height, chart }: BarChartProps) => {
  const d3Container = useRef<SVGSVGElement>(null);
  useEffect(() => {
    const marginTop = 20;
    const marginBottom = 30;
    const marginRight = 0;
    const marginLeft = 40;
    const xRange = [marginLeft, width - marginRight]; // [left, right]
    const yRange = [height - marginBottom, marginTop]; // [bottom, top]
    const xPadding = 0.1;
    const yType = d3.scaleLinear;
    const yFormat = 'f';
    if (d3Container.current && chart) {
      const { entries: data, label: yLabel } = chart;
      const x = (d) => d.key;
      const y = (d) => d.value;
      const color = 'steelblue';
      // Compute values.
      const X = d3.map(data, x);
      const Y = d3.map(data, y);

      // Compute default domains, and unique the x-domain.
      const xDomain = new d3.InternSet(X);
      const yDomain = [0, d3.max(Y)];

      // Omit any data not present in the x-domain.
      const I = d3.range(X.length).filter((i) => xDomain.has(X[i]));

      // Construct scales, axes, and formats.
      const xScale = d3.scaleBand(xDomain, xRange).padding(xPadding);
      const yScale = yType(yDomain, yRange);
      const xAxis = d3.axisBottom(xScale).tickSizeOuter(0);
      const yAxis = d3.axisLeft(yScale).ticks(height / 40, yFormat);

      // Compute titles.
      const formatValue = yScale.tickFormat(100, yFormat);
      const title = (i) => `${X[i]}\n${formatValue(Y[i])}`;

      const selection = d3.select(d3Container.current);
      selection.selectAll('*').remove(); // Remove existing content.
      const svg = selection
        .attr('viewBox', [0, 0, width, height])
        .attr('style', `max-width: 100%; max-height: ${height}; height: auto; height: intrinsic;`);

      svg
        .append('g')
        .attr('transform', `translate(${marginLeft},0)`)
        .call(yAxis)
        .call((g) => g.select('.domain').remove())
        .call((g) =>
          g
            .selectAll('.tick line')
            .clone()
            .attr('x2', width - marginLeft - marginRight)
            .attr('stroke-opacity', 0.1)
        )
        .call((g) =>
          g
            .append('text')
            .attr('x', -marginLeft)
            .attr('y', 10)
            .attr('fill', 'currentColor')
            .attr('text-anchor', 'start')
            .text(yLabel)
        );

      const bar = svg
        .append('g')
        .attr('fill', color)
        .selectAll('rect')
        .data(I)
        .join('rect')
        .attr('x', (i) => xScale(X[i]))
        .attr('y', (i) => yScale(Y[i]))
        .attr('height', (i) => yScale(0) - yScale(Y[i]))
        .attr('width', xScale.bandwidth());

      if (title) {
        bar.append('title').text(title);
      }

      svg
        .append('g')
        .attr('transform', `translate(0,${height - marginBottom})`)
        .call(xAxis);
    }
  }, [width, height, chart]);
  return (
    <div>
      <svg ref={d3Container} width={width} height={height} />
    </div>
  );
};

const PieChartComponent = ({ width, height, chart }: PieChartProps) => {
  const d3Container = useRef<SVGSVGElement>(null);
  useEffect(() => {
    const { entries: data } = chart;
    const name = (d) => d.key; // given d in data, returns the (ordinal) label
    const value = (d) => d.value; // given d in data, returns the (quantitative) value
    const innerRadius = 0; // inner radius of pie, in pixels (non-zero for donut)
    const outerRadius = Math.min(width, height) / 2; // outer radius of pie, in pixels
    const labelRadius = innerRadius * 0.2 + outerRadius * 0.8; // center radius of labels
    const format = ','; // a format specifier for values (in the label)
    const stroke = innerRadius > 0 ? 'none' : 'white'; // stroke separating widths
    const strokeWidth = 1; // width of stroke separating wedges
    const strokeLinejoin = 'round'; // line join of stroke separating wedges
    const padAngle = stroke === 'none' ? 1 / outerRadius : 0; // angular separation between wedges
    // Compute values.
    const N = d3.map(data, name);
    const V = d3.map(data, value);
    const I = d3.range(N.length).filter((i) => !isNaN(V[i]));

    // Unique the names.
    const names = new d3.InternSet(N);

    // Chose a default color scheme based on cardinality.
    let colors = d3.schemeSpectral[names.size];
    if (colors === undefined) colors = d3.quantize((t) => d3.interpolateSpectral(t * 0.8 + 0.1), names.size);

    // Construct scales.
    const color = d3.scaleOrdinal(names, colors);

    // Compute titles.
    const formatValue = d3.format(format);
    const title = (i) => `${N[i]}\n${formatValue(V[i])}`;

    const label = (d) => {
      const lines = `${title(d.data)}`.split(/\n/);
      let value: string[] = d.endAngle - d.startAngle > 0.25 ? lines : lines.slice(0, 1);
      return value.map((v) => {
        if (v.length > 13) {
          return v.substring(0, 10).concat('...');
        }
        return v;
      });
    };

    // Construct arcs.
    const arcs = d3
      .pie()
      .padAngle(padAngle)
      .sort(null)
      .value((i) => V[i])(I);
    const arc = d3.arc().innerRadius(innerRadius).outerRadius(outerRadius);
    const arcLabel = d3.arc().innerRadius(labelRadius).outerRadius(labelRadius);

    const selection = d3.select(d3Container.current);
    selection.selectAll('*').remove(); // Remove existing content.
    const svg = selection
      .attr('width', width)
      .attr('height', height)
      .attr('viewBox', [-width / 2, -height / 2, width, height])
      .attr('style', 'max-width: 100%; height: auto; height: intrinsic;');

    svg
      .append('g')
      .attr('stroke', stroke)
      .attr('stroke-width', strokeWidth)
      .attr('stroke-linejoin', strokeLinejoin)
      .selectAll('path')
      .data(arcs)
      .join('path')
      .attr('fill', (d) => color(N[d.data]))
      .attr('d', arc)
      .append('title')
      .text((d) => title(d.data));

    svg
      .append('g')
      .attr('font-family', 'sans-serif')
      .attr('font-size', 10)
      .attr('text-anchor', 'middle')
      .selectAll('text')
      .data(arcs)
      .join('text')
      .attr('transform', (d) => `translate(${arcLabel.centroid(d)})`)
      .selectAll('tspan')
      .data(label)
      .join('tspan')
      .attr('x', 0)
      .attr('y', (_, i) => `${i * 1.1}em`)
      .attr('font-weight', (_, i) => (i ? null : 'bold'))
      .text((d) => d);

    Object.assign(svg.node(), { scales: { color } });
  }, [width, height, chart]);
  return (
    <div>
      <svg ref={d3Container} />
    </div>
  );
};
