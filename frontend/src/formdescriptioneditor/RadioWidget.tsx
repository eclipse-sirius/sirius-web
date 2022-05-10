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
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { WidgetProps } from 'formdescriptioneditor/WidgetEntry.types';
import React, { useEffect, useRef, useState } from 'react';

const useStyles = makeStyles((theme) => ({
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const RadioWidget = ({ widget, selection }: WidgetProps) => {
  const classes = useStyles();

  const [selected, setSelected] = useState<boolean>(false);

  const ref = useRef<HTMLInputElement | null>(null);

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
      <RadioGroup
        data-testid={widget.label}
        aria-label={widget.label}
        name={widget.label}
        defaultValue={'value2'}
        row
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
      >
        <FormControlLabel
          value={'value1'}
          control={<Radio color="primary" data-testid={'value1'} />}
          label={'Value 1'}
          checked={false}
        />
        <FormControlLabel
          value={'value2'}
          control={<Radio color="primary" data-testid={'value2'} inputRef={ref} />}
          label={'Value 2'}
          checked={true}
        />
        <FormControlLabel
          value={'value3'}
          control={<Radio color="primary" data-testid={'value2'} />}
          label={'Value 3'}
          checked={false}
        />
      </RadioGroup>
    </div>
  );
};
