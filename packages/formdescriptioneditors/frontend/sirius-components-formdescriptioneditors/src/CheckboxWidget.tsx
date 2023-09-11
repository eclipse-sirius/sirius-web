/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { CheckboxStyleProps } from '@eclipse-sirius/sirius-components-forms';
import Checkbox from '@material-ui/core/Checkbox';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useEffect, useRef, useState } from 'react';
import { CheckboxWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme, CheckboxStyleProps>((theme) => ({
  style: {
    color: ({ color }) => (color ? getCSSColor(color, theme) : theme.palette.primary.light),
    '&.Mui-checked': {
      color: ({ color }) => (color ? getCSSColor(color, theme) : theme.palette.primary.light),
    },
  },
  selected: {
    color: theme.palette.primary.main,
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

export const CheckboxWidget = ({ widget, selection }: CheckboxWidgetProps) => {
  const props: CheckboxStyleProps = {
    color: widget.style?.color ?? null,
  };
  const classes = useStyles(props);

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
    <FormControlLabel
      labelPlacement={widget.style?.labelPlacement ?? 'end'}
      label={
        <div className={classes.propertySectionLabel}>
          <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
            {widget.label}
          </Typography>
          {widget.hasHelpText ? (
            <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} />
          ) : null}
        </div>
      }
      control={
        <Checkbox
          data-testid={widget.label}
          checked
          inputRef={ref}
          onFocus={() => setSelected(true)}
          onBlur={() => setSelected(false)}
          classes={{ root: classes.style }}
        />
      }
    />
  );
};
