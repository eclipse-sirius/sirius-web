/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { getCSSColor, useSelection } from '@eclipse-sirius/sirius-components-core';
import { CheckboxStyleProps, getFlexProperties } from '@eclipse-sirius/sirius-components-forms';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import Checkbox from '@mui/material/Checkbox';
import FormControl from '@mui/material/FormControl';
import Typography from '@mui/material/Typography';
import { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { CheckboxWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<CheckboxStyleProps>()((theme, { color, flexProps }) => {
  const { flexDirectionCSS, alignItems, gap, labelFlex, valueFlex } = getFlexProperties(flexProps, 'row-reverse');
  return {
    style: {
      color: color ? getCSSColor(color, theme) : theme.palette.primary.light,
      '&.Mui-checked': {
        color: color ? getCSSColor(color, theme) : theme.palette.primary.light,
      },
    },
    selected: {
      color: theme.palette.primary.main,
    },
    propertySectionLabel: {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
      flex: labelFlex ?? '1 1 auto',
    },
    propertySection: {
      display: 'flex',
      flexDirection: flexDirectionCSS,
      alignItems,
      gap: gap ?? theme.spacing(0.5),
    },
    propertySectionValue: {
      flex: valueFlex ?? '',
    },
  };
});

export const CheckboxWidget = ({ widget }: CheckboxWidgetProps) => {
  const props: CheckboxStyleProps = {
    color: widget.style?.color ?? null,
    flexProps: widget.style?.widgetFlexboxLayout ?? null,
  };
  const { classes } = useStyles(props);

  const [selected, setSelected] = useState<boolean>(false);
  const { selection } = useSelection();

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
    <FormControl classes={{ root: classes.propertySection }}>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <div className={classes.propertySectionValue}>
        <Checkbox
          data-testid={widget.label}
          checked
          inputRef={ref}
          onFocus={() => setSelected(true)}
          onBlur={() => setSelected(false)}
          classes={{ root: classes.style }}
        />
      </div>
    </FormControl>
  );
};
