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
import * as d3 from 'd3';
import { useEffect, useRef } from 'react';
import { getFontSize, getFontStyle, getFontWeight, getTextDecoration } from '../chartOperations';
import { BarChartProps } from './BarChart.types';

const marginTop = 30;
const marginBottom = 30;
const marginRight = 0;
const marginLeft = 50;
const xPadding = 0.1;
const yType = d3.scaleLinear;
const yFormat = 'f';
const x = (d) => d.key;
const y = (d) => d.value;

export const BarChart = ({ chart }: BarChartProps) => {
  const d3Container = useRef<SVGSVGElement | null>(null);

  useEffect(() => {
    const width = chart.width;
    const height = chart.height;
    const xRange = [marginLeft, width - marginRight]; // [left, right]
    const yRange = [height - marginBottom, marginTop]; // [bottom, top]

    if (d3Container.current && chart) {
      const { entries: data, label: yLabel, style } = chart;
      const fontSize = getFontSize(style);
      const fontStyle = getFontStyle(style);
      const fontWeight = getFontWeight(style);
      const textDecoration = getTextDecoration(style);
      let color: string;
      if (style?.barsColor != null && style?.barsColor.length > 0) {
        color = style?.barsColor;
      } else {
        color = 'steelblue';
      }
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
      const svg = selection.attr('viewBox', [0, 0, width, height]).attr('pointer-events', 'none'); // allow selection anywhere in the svg

      svg
        .append('g')
        .attr('transform', `translate(${marginLeft},0)`)
        .attr(
          'style',
          `font-size:${fontSize}; font-style: ${fontStyle}; text-decoration: ${textDecoration}; font-weight: ${fontWeight}`
        )
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
        .attr(
          'style',
          `font-size:${fontSize}; font-style: ${fontStyle}; text-decoration: ${textDecoration}; font-weight: ${fontWeight}`
        )
        .call(xAxis);
    }
  }, [chart, d3Container]);

  return <svg ref={d3Container} width={chart.width} height={chart.height} />;
};
