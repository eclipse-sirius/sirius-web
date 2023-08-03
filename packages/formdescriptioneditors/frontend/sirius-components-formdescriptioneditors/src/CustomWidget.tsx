/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import ExtensionIcon from '@material-ui/icons/Extension';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useEffect, useRef, useState } from 'react';
import { CustomWidgetProps } from './CustomWidget.types';

type CustomWidgetStyleProps = {};
const useStyles = makeStyles<Theme, CustomWidgetStyleProps>((theme) => ({
  style: {
    color: theme.palette.secondary.main,
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

/**
 * Generic fallback component to display custom widgets which do not provide a more
 * specific implementation. This allows them to still be created/displayed/selected/moved
 * in a Form Description Editor, but they will be represented by a generic icon.
 */
export const CustomWidget = ({ widget }: CustomWidgetProps) => {
  const props: CustomWidgetStyleProps = {};
  const classes = useStyles(props);

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
    <div>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <div
        ref={ref}
        onFocus={() => setSelected(true)}
        onBlur={() => setSelected(false)}
        tabIndex={0}
        className={classes.style}>
        <ExtensionIcon />
      </div>
    </div>
  );
};
