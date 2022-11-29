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
import { BarChart, BarChartRepresentation } from '@eclipse-sirius/sirius-components-charts';
import { GQLBarChart } from '@eclipse-sirius/sirius-components-forms';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useEffect, useRef, useState } from 'react';
import { BarChartWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme>((theme) => ({
  selected: {
    color: theme.palette.primary.main,
  },
  chart: {
    overflowX: 'auto',
  },
}));

export const BarChartWidget = ({ widget, selection }: BarChartWidgetProps) => {
  const classes = useStyles();
  const barChartWidget = widget.chart as GQLBarChart;

  const chart: BarChartRepresentation = {
    label: 'Frequency',
    entries: [
      { key: 'A', value: 0.08167 },
      { key: 'B', value: 0.01492 },
      { key: 'C', value: 0.02782 },
      { key: 'D', value: 0.04253 },
      { key: 'E', value: 0.12702 },
      { key: 'F', value: 0.02288 },
      { key: 'G', value: 0.02015 },
      { key: 'H', value: 0.06094 },
      { key: 'I', value: 0.06966 },
      { key: 'J', value: 0.00153 },
      { key: 'K', value: 0.00772 },
      { key: 'L', value: 0.04025 },
      { key: 'M', value: 0.02406 },
      { key: 'N', value: 0.06749 },
      { key: 'O', value: 0.07507 },
      { key: 'P', value: 0.01929 },
      { key: 'Q', value: 0.00095 },
      { key: 'R', value: 0.05987 },
      { key: 'S', value: 0.06327 },
      { key: 'T', value: 0.09056 },
      { key: 'U', value: 0.02758 },
      { key: 'V', value: 0.00978 },
      { key: 'W', value: 0.0236 },
      { key: 'X', value: 0.0015 },
      { key: 'Y', value: 0.01974 },
      { key: 'Z', value: 0.00074 },
    ],
    style: barChartWidget.style,
    width: barChartWidget.width,
    height: barChartWidget.height,
  };
  const [selected, setSelected] = useState<boolean>(false);

  const ref = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [selection, widget]);

  return (
    <div
      data-testid={barChartWidget.label}
      onFocus={() => setSelected(true)}
      onBlur={() => setSelected(false)}
      ref={ref}
      tabIndex={0}>
      <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
        {barChartWidget.label}
      </Typography>
      <div className={classes.chart}>
        <BarChart chart={chart} />
      </div>
    </div>
  );
};
