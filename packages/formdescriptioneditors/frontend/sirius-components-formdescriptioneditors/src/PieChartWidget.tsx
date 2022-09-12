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
import { PieChart, PieChartRepresentation } from '@eclipse-sirius/sirius-components-charts';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useEffect, useRef, useState } from 'react';
import { WidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles((theme) => ({
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const PieChartWidget = ({ widget, selection }: WidgetProps) => {
  const classes = useStyles();
  const chart: PieChartRepresentation = {
    entries: [
      { key: '<5', value: 19912018 },
      { key: '5-9', value: 20501982 },
      { key: '10-14', value: 20679786 },
      { key: '15-19', value: 21354481 },
      { key: '20-24', value: 22604232 },
      { key: '25-29', value: 21698010 },
      { key: '30-34', value: 21183639 },
      { key: '35-39', value: 19855782 },
      { key: '40-44', value: 20796128 },
      { key: '45-49', value: 21370368 },
      { key: '50-54', value: 22525490 },
      { key: '55-59', value: 21001947 },
      { key: '60-64', value: 18415681 },
      { key: '65-69', value: 14547446 },
      { key: '70-74', value: 10587721 },
      { key: '75-79', value: 7730129 },
      { key: '80-84', value: 5811429 },
      { key: '≥85', value: 5938752 },
    ],
    style: null,
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
    <div>
      <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
        {widget.label}
      </Typography>
      <div
        data-testid={widget.label}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
        ref={ref}
        tabIndex={0}>
        <PieChart width={300} height={300} chart={chart} />
      </div>
    </div>
  );
};
