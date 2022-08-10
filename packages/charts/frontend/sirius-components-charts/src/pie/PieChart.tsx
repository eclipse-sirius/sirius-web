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
// This comment will be removed in the future but we should not have to wait to solve
// all d3 relatd typing issues in order to move forward with our TypeScript checks
// @ts-nocheck
import * as d3 from 'd3';
import { useEffect, useRef } from 'react';
import { PieChartProps } from './PieChart.types';

export const PieChart = ({ width, height, chart }: PieChartProps) => {
  const d3Container = useRef<SVGSVGElement | null>(null);

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
  }, [width, height, chart, d3Container]);

  return <svg ref={d3Container} />;
};
