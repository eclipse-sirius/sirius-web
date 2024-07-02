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
import { ServerContext, ServerContextValue, useSelection } from '@eclipse-sirius/sirius-components-core';
import { ImageStyleProps } from '@eclipse-sirius/sirius-components-forms';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import ImageIcon from '@mui/icons-material/Image';
import Typography from '@mui/material/Typography';
import { useContext, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ImageWidgetState } from './ImageWidget.types';
import { ImageWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<ImageStyleProps>()((theme, { maxWidth }) => {
  let gridTemplateColumns: string;
  if (maxWidth) {
    let max = maxWidth;
    if (maxWidth.match(/[0-9]$/)) {
      max = maxWidth + 'px';
    }
    gridTemplateColumns = `minmax(auto, ${max})`;
  } else {
    gridTemplateColumns = '1fr';
  }
  return {
    container: {
      display: 'grid',
      gridTemplateColumns,
    },
    selected: {
      color: theme.palette.primary.main,
    },
    propertySectionLabel: {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
    },
  };
});

export const ImageWidget = ({ widget }: ImageWidgetProps) => {
  const props: ImageStyleProps = {
    maxWidth: widget.maxWidth,
  };
  const { classes } = useStyles(props);
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const initialState: ImageWidgetState = { imageURL: widget.url, validImage: false, selected: false };
  const [state, setState] = useState<ImageWidgetState>(initialState);
  const { selection } = useSelection();

  const onErrorLoadingImage = () => {
    setState((prevState) => {
      return {
        ...prevState,
        validImage: false,
      };
    });
  };

  useEffect(() => {
    let newURL: string = null;
    let validURL = true;
    if (!widget.url) {
      validURL = false;
    } else if (widget.url.startsWith('http://') || widget.url.startsWith('https://')) {
      newURL = widget.url;
    } else {
      newURL = httpOrigin + widget.url;
    }
    setState((prevState) => {
      return {
        ...prevState,
        imageURL: newURL,
        validImage: validURL,
      };
    });
  }, [widget.url]);

  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setState((prevState) => {
        return {
          ...prevState,
          selected: true,
        };
      });
    } else {
      setState((prevState) => {
        return {
          ...prevState,
          selected: false,
        };
      });
    }
  }, [selection, widget]);

  let imageElement: React.ReactElement | null = null;
  if (state.validImage && state.imageURL) {
    imageElement = <img id={widget.id} src={state.imageURL} width="100%" onError={onErrorLoadingImage} />;
  } else {
    imageElement = <ImageIcon style={{ fontSize: 72 }} color={'secondary'} />;
  }

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={state.selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <div
        onFocus={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: true,
            };
          })
        }
        onBlur={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: false,
            };
          })
        }
        ref={ref}
        tabIndex={0}
        className={classes.container}>
        {imageElement}
      </div>
    </div>
  );
};
