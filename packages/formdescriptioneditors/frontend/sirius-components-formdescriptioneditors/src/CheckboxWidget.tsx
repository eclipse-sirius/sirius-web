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
import Checkbox from '@material-ui/core/Checkbox';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useEffect, useRef, useState } from 'react';
import { readStyleProperty } from './styleProperties';
import { WidgetProps } from './WidgetEntry.types';

export interface StyleProps {
  color: string | null;
}
const useStyle = makeStyles<Theme, StyleProps>((theme) => ({
  style: {
    color: ({ color }) => (color ? color : theme.palette.primary.light),
    '&.Mui-checked': {
      color: ({ color }) => (color ? color : theme.palette.primary.light),
    },
  },
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const CheckboxWidget = ({ widget, selection }: WidgetProps) => {
  const props: StyleProps = {
    color: readStyleProperty(widget, 'color'),
  };
  const classes = useStyle(props);
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
      <Checkbox
        data-testid={widget.label}
        checked
        inputRef={ref}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
        classes={{ root: classes.style }}
      />
    </div>
  );
};
